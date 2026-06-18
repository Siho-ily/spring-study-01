package com.sihoily.tilboard.member.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message="ID는 빈 값일 수 없습니다.")
    private String userId;
    @NotBlank(message="PW는 빈 값일 수 없습니다.")
    private String password;
}
