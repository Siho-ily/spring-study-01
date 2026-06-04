package com.sihoily.tilboard.member.application.port.out;

import com.sihoily.tilboard.member.domain.Member;

public interface LoadMemberPort {
    Member loadMember(String email);
}
