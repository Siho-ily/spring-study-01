package com.sihoily.tilboard.member.application.service;

import com.sihoily.tilboard.member.application.port.in.LoginUseCase;
import com.sihoily.tilboard.member.application.port.in.SignupUseCase;
import com.sihoily.tilboard.member.application.port.out.LoadMemberPort;
import com.sihoily.tilboard.member.application.port.out.SaveMemberPort;
import com.sihoily.tilboard.member.domain.Member;
import com.sihoily.tilboard.member.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class MemberService implements LoginUseCase, SignupUseCase {
    private final PasswordEncoder passwordEncoder;

    private final LoadMemberPort loadMemberPort;
    private final SaveMemberPort saveMemberPort;

    @Override
    public Member login(String userId, String password) {
        return null;
    }

    @Override
    public Member signup(String userId, String email, String password, String nickname) {
        // 1. 중복 검증
        // 1-1. userId

        // 1-2. email


        // 1-3. nickname


        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);
        Member member = new Member(null, userId, email, encodedPassword, nickname, Role.USER, null, null);
        return saveMemberPort.saveMember(member);
    }
}
