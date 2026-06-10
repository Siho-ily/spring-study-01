package com.sihoily.tilboard.member.application.service;

import com.sihoily.tilboard.member.application.port.out.LoadMemberPort;
import com.sihoily.tilboard.member.application.port.out.SaveMemberPort;
import com.sihoily.tilboard.member.domain.Member;
import com.sihoily.tilboard.member.domain.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @Mock
    private SaveMemberPort saveMemberPort;
    @Mock
    private LoadMemberPort loadMemberPort;

    @InjectMocks
    private Service memberService;

    @Test
    @DisplayName("회원가입 성공")
    void testSignupSuccess() {
        // Given - 옳바른 가입 정보, 중복된 데이터가 없을 때
        String email = "email@example.com";
        String userId = "test";
        String password = "test1234!";
        String nickname = "nickname";

        Member savedMember = new Member(1L, userId, email, password, nickname, Role.USER, null, null);
        when(saveMemberPort.saveMember(any())).thenReturn(savedMember);

        // When - 회원 가입을 요청하면
        Member result = memberService.signup(userId, email, password, nickname);

        // Then - 회원가입 성공하고 회원 정보가 반환된다.
        // 결과 값이 맞는지 검증
        assertThat(result.email()).isEqualTo(email);
        assertThat(result.nickname()).isEqualTo(nickname);

        // 실제로 호출 됐는지 검증
        verify(saveMemberPort).saveMember(any());
    }
}