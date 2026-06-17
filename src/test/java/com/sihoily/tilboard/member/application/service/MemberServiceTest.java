package com.sihoily.tilboard.member.application.service;

import com.sihoily.tilboard.global.security.jwt.JwtProvider;
import com.sihoily.tilboard.member.application.port.out.LoadMemberPort;
import com.sihoily.tilboard.member.application.port.out.SaveMemberPort;
import com.sihoily.tilboard.member.application.result.LoginResult;
import com.sihoily.tilboard.member.domain.Member;
import com.sihoily.tilboard.member.domain.Role;
import com.sihoily.tilboard.member.exception.MemberConflictException;
import com.sihoily.tilboard.member.exception.MemberNotFoundException;
import com.sihoily.tilboard.member.exception.MemberPasswordVerifyFailed;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @Mock
    private SaveMemberPort saveMemberPort;
    @Mock
    private LoadMemberPort loadMemberPort;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private MemberService memberService;

    @Nested
    @DisplayName("회원가입 테스트")
    class Signup{
        @Nested
        @DisplayName("회원가입 성공 케이스")
        class SuccessCase {
            @Test
            @DisplayName("회원가입 성공")
            void signupSuccess() {
                // Given - 옳바른 가입 정보, 중복된 데이터가 없을 때
                String email = "email@example.com";
                String userId = "test";
                String password = "test1234!";
                String encodedPassword = "encodedPassword";
                String nickname = "nickname";

                Member savedMember = new Member(1L, userId, email, encodedPassword, nickname, Role.USER, null, null);
                when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
                when(saveMemberPort.saveMember(any())).thenReturn(savedMember);

                // When - 회원 가입을 요청하면
                Member result = memberService.signup(userId, email, password, nickname);

                // Then - 회원가입 성공하고 회원 정보가 반환된다.
                // 결과 값이 맞는지 검증
                assertThat(result.email()).isEqualTo(email);
                assertThat(result.nickname()).isEqualTo(nickname);

                // 실제로 호출 됐는지 검증
                verify(saveMemberPort).saveMember(any());
                verify(passwordEncoder).encode(password);
            }
        }

        @Nested
        @DisplayName("회원가입 실패 케이스")
        class FailureCase {
            @Test
            @DisplayName("유저 아이디 중복으로 인한 회원가입 실패")
            void signupFailByDuplicatedUserId() {
                // Given - 유저 아이디 중복
                String email = "email@example.com";
                String userId = "test";
                String password = "test1234!";
                String nickname = "nickname";

                when(loadMemberPort.existsByUserId(any())).thenReturn(true);
                when(loadMemberPort.existsByEmail(any())).thenReturn(false);
                when(loadMemberPort.existsByNickname(any())).thenReturn(false);

                // When & Then - 회원가입을 요청하면 멤버 중복 에러가 발생한다.
                // 결과 값이 맞는지 검증
                assertThatThrownBy(() -> memberService.signup(userId, email, password, nickname))
                        .isInstanceOf(MemberConflictException.class);

                // 실제로 호출 됐는지 검증
                verify(loadMemberPort).existsByUserId(any());
                verify(loadMemberPort).existsByEmail(any());
                verify(loadMemberPort).existsByNickname(any());
            }

            @Test
            @DisplayName("이메일 중복으로 인한 회원가입 실패")
            void signupFailByDuplicatedEmail() {
                // Given - 이메일 중복
                String email = "email@example.com";
                String userId = "test";
                String password = "test1234!";
                String nickname = "nickname";

                when(loadMemberPort.existsByUserId(any())).thenReturn(false);
                when(loadMemberPort.existsByEmail(any())).thenReturn(true);
                when(loadMemberPort.existsByNickname(any())).thenReturn(false);

                // When & Then - 회원가입을 요청하면 멤버 중복 에러가 발생한다.
                // 결과 값이 맞는지 검증
                assertThatThrownBy(() -> memberService.signup(userId, email, password, nickname))
                        .isInstanceOf(MemberConflictException.class);

                // 실제로 호출 됐는지 검증
                verify(loadMemberPort).existsByUserId(any());
                verify(loadMemberPort).existsByEmail(any());
                verify(loadMemberPort).existsByNickname(any());
            }

            @Test
            @DisplayName("닉네임 중복으로 인한 회원가입 실패")
            void signupFailByDuplicatedNickname() {
                // Given - 닉네임 중복
                String email = "email@example.com";
                String userId = "test";
                String password = "test1234!";
                String nickname = "nickname";

                when(loadMemberPort.existsByUserId(any())).thenReturn(false);
                when(loadMemberPort.existsByEmail(any())).thenReturn(false);
                when(loadMemberPort.existsByNickname(any())).thenReturn(true);

                // When & Then - 회원가입을 요청하면 멤버 중복 에러가 발생한다.
                // 결과 값이 맞는지 검증
                assertThatThrownBy(() -> memberService.signup(userId, email, password, nickname))
                        .isInstanceOf(MemberConflictException.class);

                // 실제로 호출 됐는지 검증
                verify(loadMemberPort).existsByUserId(any());
                verify(loadMemberPort).existsByEmail(any());
                verify(loadMemberPort).existsByNickname(any());
            }
        }
    }


    @Nested
    @DisplayName("로그인 테스트")
    class Login{
        @Nested
        @DisplayName("로그인 성공 케이스")
        class SuccessCase {
            @Test
            @DisplayName("로그인 성공")
            void loginSuccess() {
                // Given - 옳바른 유저 정보 & DB에 로그인 데이터가 있을 때
                String userId = "test";
                String password = "test1234!";
                String encodedPassword = "encodedPassword";

                Optional<Member> member = Optional.of(
                        new Member(1L, userId, "test@example.com", encodedPassword, "testname", Role.USER, null, null)
                );

                when(loadMemberPort.loadMember(any())).thenReturn(member);
                when(passwordEncoder.matches(eq(password), any(String.class))).thenReturn(true);
                when(jwtProvider.generateAccessToken(eq(userId), any(String.class))).thenReturn("accesstoken");
                when(jwtProvider.generateRefreshToken(any(String.class))).thenReturn("refreshtoken");

                // When - 로그인을 요청하면
                LoginResult result = memberService.login(userId, password);

                // Then - 로그인 유저 정보와 토큰이 담긴 데이터가 반환된다
                assertThat(result.member().userId()).isEqualTo(userId);
                assertThat(result.token().accessToken()).isEqualTo("accesstoken");
                assertThat(result.token().refreshToken()).isEqualTo("refreshtoken");

                verify(loadMemberPort).loadMember(userId);
                verify(passwordEncoder).matches(eq(password), any(String.class));
                verify(jwtProvider).generateAccessToken(eq(userId), any(String.class));
                verify(jwtProvider).generateRefreshToken(userId);
            }
        }

        @Nested
        @DisplayName("로그인 실패 케이스")
        class FailureCase {
            @Test
            @DisplayName("회원 정보가 없는 경우 로그인 실패")
            void loginFailByMemberNotFound() {
                // Given
                String userId = "test";
                String password = "test1234!";

                // When - 회원 정보가 없는 userId로 로그인을 시도하면, 회원 정보가 반환되지 않는다.
                when(loadMemberPort.loadMember(any(String.class))).thenReturn(Optional.empty());

                // Then
                assertThatThrownBy(() -> memberService.login(userId, password))
                        .isInstanceOf(MemberNotFoundException.class);

                verify(loadMemberPort).loadMember(userId);
            }

            @Test
            @DisplayName("비밀번호가 일치하지 않는 경우 로그인 실패")
            void loginFailByPasswordMismatch() {
                // Given
                String userId = "test";
                String password = "test1234!";
                String encodedPassword = "encodedPassword";

                Optional<Member> member = Optional.of(
                        new Member(1L, userId, "test@example.com", encodedPassword, "testname", Role.USER, null, null)
                );

                // When
                when(loadMemberPort.loadMember(any(String.class))).thenReturn(member);
                when(passwordEncoder.matches(eq(password), any(String.class))).thenReturn(false);

                // Then
                assertThatThrownBy(() -> memberService.login(userId, password))
                    .isInstanceOf(MemberPasswordVerifyFailed.class);

                verify(loadMemberPort).loadMember(userId);
                verify(passwordEncoder).matches(eq(password), any(String.class));
            }
        }
    }
}
