package com.sihoily.tilboard.global.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 클라이언트
    BAD_REQUEST(HttpStatus.BAD_REQUEST,  "유효하지 않은 요청 파라미터가 있습니다."),
    REQUEST_VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "요청 필드 검증에 실패하였습니다."),

    // 리소스 검증
    OCCUPIED_REQUEST_PARAMETER(HttpStatus.CONFLICT,  "이미 사용 중인 요청 파라미터입니다."),

    // 인증
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인이 필요한 리소스입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED,  "토큰이 만료되었습니다. 다시 인증하여 주세요."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "잘못된 토큰 형식입니다. 다시 인증하여 주세요."),
    AUTHORIZATION_FAILED(HttpStatus.UNAUTHORIZED, "인증에 실패하였습니다. 다시 시도하여 주세요."),

    // 회원,
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND,  "회원 정보를 찾을 수 없습니다."),

    // 서버
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생하였습니다.");

    private final HttpStatus status;
    private final String message;
}
