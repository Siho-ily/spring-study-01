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
    @NotBlank(message = "빈 문자열은 허용되지 않습니다.")
    @AlphaNumberic
    @Size(min = 1, max = 100, message = "아이디는 1~100자 이내만 허용됩니다.")
    private String userId;

    @NotBlank(message = "빈 문자열은 허용되지 않습니다.")
    @Pattern(regexp = "[a-zA-Z0-9!@#$%^&*()_=+-]+", message = "영·숫자 및 특수문자(!@#$%^&*()_=+-)만 허용됩니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8~20자 이내만 허용됩니다.")
    private String password;

    @NotBlank(message = "빈 문자열은 허용되지 않습니다.")
    @Email(message = "이메일 형식이 일치하지 않습니다.")
    private String email;

    @NotBlank
    @AlphaKoreanNumberic
    @Size(min = 2, max = 50)
    private String nickname;
}
