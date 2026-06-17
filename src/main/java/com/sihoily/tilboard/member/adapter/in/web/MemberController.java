package com.sihoily.tilboard.member.adapter.in.web;

import com.sihoily.tilboard.global.response.ApiResponse;
import com.sihoily.tilboard.member.adapter.in.web.dto.request.LoginRequest;
import com.sihoily.tilboard.member.adapter.in.web.dto.request.SignupRequest;
import com.sihoily.tilboard.member.adapter.in.web.dto.response.LoginResponse;
import com.sihoily.tilboard.member.adapter.in.web.dto.response.SignupResponse;
import com.sihoily.tilboard.member.application.port.in.LoginUseCase;
import com.sihoily.tilboard.member.application.port.in.SignupUseCase;
import com.sihoily.tilboard.member.application.result.LoginResult;
import com.sihoily.tilboard.member.domain.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
//@Validated
public class MemberController {
    private final LoginUseCase loginUseCase;
    private final SignupUseCase signupUseCase;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignupResponse>> signup(
            @Valid @RequestBody SignupRequest request
    ) {
        Member member = signupUseCase.signup(request.getUserId(), request.getEmail(), request.getPassword(), request.getNickname());
        SignupResponse response = SignupResponse.from(member);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
        @Valid @RequestBody LoginRequest request
    ) {
        LoginResult loginResult = loginUseCase.login(request.getUserId(), request.getPassword());
        LoginResponse response = LoginResponse.from(loginResult);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }
}
