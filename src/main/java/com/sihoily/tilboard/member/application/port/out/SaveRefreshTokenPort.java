package com.sihoily.tilboard.member.application.port.out;

import java.time.LocalDateTime;

public interface SaveRefreshTokenPort {
    void saveRefreshToken(String userId, String refreshToken, LocalDateTime expiration);
}
