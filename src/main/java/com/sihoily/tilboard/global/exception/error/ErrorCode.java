package com.sihoily.tilboard.global.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 인증
    UNAUTHORIZED("AUTH-1", HttpStatus.UNAUTHORIZED, "로그인이 필요한 리소스입니다."),
    EXPIRED_TOKEN("AUTH-2", HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다. 다시 인증하여 주세요."),
    INVALID_TOKEN("AUTH-3", HttpStatus.UNAUTHORIZED, "잘못된 토큰 형식입니다. 다시 인증하여 주세요."),

    // 회원
    MEMBER_NOT_FOUND("MEMBER-1", HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다."),
    DUPLICATED_USER_ID("MEMBER-2", HttpStatus.CONFLICT, "이미 사용 중인 아이디입니다."),
    DUPLICATED_EMAIL("MEMBER-3", HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    DUPLICATED_NICKNAME("MEMBER-4", HttpStatus.CONFLICT, "이미 사용 중인 닉네임입니다."),

    // 서버
    INTERNAL_SERVER_ERROR("SR-1", HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생하였습니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;
}
