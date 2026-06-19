package com.sihoily.tilboard.member.application.port.out;

public interface LoadRefreshTokenPort {
    RefreshTokenResult loadRefreshToken(String refreshToken);
}
