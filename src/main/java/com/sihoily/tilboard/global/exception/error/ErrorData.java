package com.sihoily.tilboard.global.exception.error;

import lombok.*;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorData {
    private String code;
    private String message;
    private Object data;

    public static ErrorData of(ErrorCode errorCode) {
        return of(errorCode, errorCode.getMessage(), null);
    }

    public static ErrorData of(ErrorCode errorCode, String message) {
        return of(errorCode, message, null);
    }

    public static ErrorData of(ErrorCode errorCode, Object data) {
        return of(errorCode, errorCode.getMessage(), data);
    }

    public static ErrorData of(ErrorCode errorCode, String message, Object data) {
        return ErrorData.builder()
                .code(errorCode.getCode())
                .message(message)
                .data(data)
                .build();
    }

    public static ErrorData field(ErrorCode errorCode, String field, Object value) {
        return ErrorData.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .data(Map.of("field", field, "value", value))
                .build();
    }
}
