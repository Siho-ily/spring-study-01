package com.sihoily.tilboard.member.adapter.in.web.dto.response;

import com.sihoily.tilboard.member.application.result.LoginResult;
import com.sihoily.tilboard.member.domain.Member;
import com.sihoily.tilboard.member.domain.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String userId;
    private String nickname;
    private String accessToken;
    private String refreshToken;

    public static LoginResponse from(LoginResult result) {
        Member member = result.member();
        Token token = result.token();

        return new LoginResponse(
                member.userId(),
                member.nickname(),
                token.accessToken(),
                token.refreshToken()
        );
    }
}
