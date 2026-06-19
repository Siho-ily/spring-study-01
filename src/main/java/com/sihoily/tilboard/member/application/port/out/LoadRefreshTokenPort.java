package com.sihoily.tilboard.member.application.port.out;

import com.sihoily.tilboard.member.application.result.RefreshTokenResult;

import java.util.Optional;

public interface LoadRefreshTokenPort {
    Optional<RefreshTokenResult> loadRefreshToken(String refreshToken);
}
