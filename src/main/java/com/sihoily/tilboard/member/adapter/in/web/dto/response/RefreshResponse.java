package com.sihoily.tilboard.member.adapter.in.web.dto.response;

import com.sihoily.tilboard.member.domain.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshResponse {
    private String accessToken;
    private String refreshToken;

    public static RefreshResponse from(Token token) {
        return new RefreshResponse(token.accessToken(), token.refreshToken());
    }
}
