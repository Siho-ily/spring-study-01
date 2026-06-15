package com.sihoily.tilboard.member.application.port.in;

import com.sihoily.tilboard.member.application.result.LoginResult;

public interface LoginUseCase {
    LoginResult login(String userId, String password);
}
