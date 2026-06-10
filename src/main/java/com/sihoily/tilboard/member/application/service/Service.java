package com.sihoily.tilboard.member.application.service;

import com.sihoily.tilboard.member.application.port.in.LoginUseCase;
import com.sihoily.tilboard.member.application.port.in.SignupUseCase;
import com.sihoily.tilboard.member.application.port.out.LoadMemberPort;
import com.sihoily.tilboard.member.application.port.out.SaveMemberPort;
import com.sihoily.tilboard.member.domain.Member;
import com.sihoily.tilboard.member.domain.Role;
import lombok.RequiredArgsConstructor;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class Service implements LoginUseCase, SignupUseCase {
    private final LoadMemberPort loadMemberPort;
    private final SaveMemberPort saveMemberPort;

    @Override
    public Member login(String email, String password) {
        return null;
    }

    @Override
    public Member signup(String userId, String email, String password, String nickname) {
        Member member = new Member(null, userId, email, password, nickname, Role.USER, null, null);
        return saveMemberPort.saveMember(member);
    }
}
