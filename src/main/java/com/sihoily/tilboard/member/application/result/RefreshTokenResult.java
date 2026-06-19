package com.sihoily.tilboard.member.application.result;

import java.time.LocalDateTime;

public record RefreshTokenResult(
        String userId,
        LocalDateTime expiresAt
) {
}
