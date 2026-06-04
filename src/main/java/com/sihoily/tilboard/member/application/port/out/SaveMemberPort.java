package com.sihoily.tilboard.member.application.port.out;

import com.sihoily.tilboard.member.domain.Member;

public interface SaveMemberPort {
    Member saveMember(Member member);
}
