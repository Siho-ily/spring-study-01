package com.sihoily.tilboard.member.adapter.in.web;

import com.sihoily.tilboard.member.adapter.in.web.dto.request.LoginRequest;
import com.sihoily.tilboard.member.adapter.in.web.dto.request.SignupRequest;
import com.sihoily.tilboard.member.adapter.in.web.dto.response.LoginResponse;
import com.sihoily.tilboard.member.adapter.in.web.dto.response.SignupResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "회원", description = "회원 관련 API")
public interface MemberControllerDocs {

    @Operation(summary = "회원가입", description = "아이디, 비밀번호, 이메일, 닉네임으로 회원가입합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "회원가입 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SignupResponse.class),
                            examples = @ExampleObject(
                                    name = "회원가입 성공",
                                    value = """
                                            {
                                              "success": true,
                                              "message": "success",
                                              "data": {
                                                "userId": "myUser123",
                                                "nickname": "닉네임",
                                                "email": "test@example.com"
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "입력값 검증 실패",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "입력값 검증 실패",
                                    value = """
                                            {
                                              "success": false,
                                              "message": "요청 필드 검증에 실패하였습니다.",
                                              "errors": [
                                                {
                                                  "message": "빈 문자열은 허용되지 않습니다.",
                                                  "data": {
                                                    "key": "userId",
                                                    "value": ""
                                                  }
                                                }
                                              ]
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "중복된 회원 정보",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "중복된 회원 정보",
                                    value = """
                                            {
                                              "success": false,
                                              "message": "중복된 회원 정보가 있습니다.",
                                              "errors": [
                                                {
                                                  "message": "아이디가 이미 사용중입니다.",
                                                  "data": {
                                                    "key": "userId",
                                                    "value": "myUser123"
                                                  }
                                                }
                                              ]
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<com.sihoily.tilboard.global.response.ApiResponse<SignupResponse>> signup(SignupRequest request);

    @Operation(summary = "로그인", description = "아이디, 비밀번호로 로그인합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class),
                            examples = @ExampleObject(
                                    name = "로그인 성공",
                                    value = """
                                            {
                                              "success": true,
                                              "message": "success",
                                              "data": {
                                                "userId": "myUser123",
                                                "nickname": "닉네임",
                                                "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
                                                "refreshToken": "eyJhbGciOiJIUzI1NiJ9..."
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "아이디 또는 비밀번호 불일치",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "아이디 또는 비밀번호 불일치",
                                    value = """
                                            {
                                              "success": false,
                                              "message": "인증에 실패하였습니다. 다시 시도하여 주세요.",
                                              "errors": [
                                                {
                                                  "message": "아이디나 비밀번호가 일치하지 않습니다.",
                                                  "data": null
                                                }
                                              ]
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "회원 정보 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "회원 정보 없음",
                                    value = """
                                            {
                                              "success": false,
                                              "message": "회원 정보를 찾을 수 없습니다.",
                                              "errors": [
                                                {
                                                  "message": "사용자 정보를 찾을 수 없습니다.",
                                                  "data": {
                                                    "key": "userId",
                                                    "value": "myUser123"
                                                  }
                                                }
                                              ]
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<com.sihoily.tilboard.global.response.ApiResponse<LoginResponse>> login(LoginRequest request);
}
