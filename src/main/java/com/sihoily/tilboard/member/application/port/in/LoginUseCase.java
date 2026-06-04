package com.sihoily.tilboard.member.application.port.in;

import com.sihoily.tilboard.member.domain.Member;

public interface LoginUseCase {
    Member login(String email, String password);
}
