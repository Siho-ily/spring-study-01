package com.sihoily.tilboard.member.adapter.in.web.dto.request;

import com.sihoily.tilboard.global.validation.annotation.AlphaKoreanNumberic;
import com.sihoily.tilboard.global.validation.annotation.AlphaNumberic;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    @NotBlank
    @AlphaNumberic
    @Size(min = 1, max = 100)
    private String userId;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9!@#$%^&*()_=+-]+")
    @Size(min = 8, max = 20)
    private String password;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @AlphaKoreanNumberic
    @Size(min = 2, max = 50)
    private String nickname;
}
