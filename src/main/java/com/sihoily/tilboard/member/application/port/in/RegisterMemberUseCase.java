package com.sihoily.tilboard.member.application.port.in;

import com.sihoily.tilboard.member.domain.Member;

public interface RegisterMemberUseCase {
    Member register(String userId, String email, String password, String nickname);
}
