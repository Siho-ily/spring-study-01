package com.sihoily.tilboard.member.application.service;

import com.sihoily.tilboard.member.application.port.out.LoadMemberPort;
import com.sihoily.tilboard.member.application.port.out.SaveMemberPort;
import com.sihoily.tilboard.member.domain.Member;
import com.sihoily.tilboard.member.domain.Role;
import com.sihoily.tilboard.member.exception.MemberConflictException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
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

    @InjectMocks
    private MemberService memberService;

    @Nested
    @DisplayName("회원가입 성공")
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
    @DisplayName("회원가입 실패")
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
