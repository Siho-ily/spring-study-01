package com.sihoily.tilboard.member.adapter.in.web.dto.response;

import com.sihoily.tilboard.member.domain.Member;
import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class SignupResponse {
    private String userId;
    private String nickname;
    private String email;

    public static SignupResponse from(Member member){
        return new SignupResponse(member.userId(),  member.nickname(), member.email());
    }
}
