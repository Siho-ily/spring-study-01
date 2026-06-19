package com.sihoily.tilboard.member.application.port.in;

import com.sihoily.tilboard.member.domain.Token;

public interface RefreshTokenUseCase {
    Token regenerateToken(String refreshToken);
}
