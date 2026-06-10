package com.sihoily.tilboard.global.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorData {
    private String type;
    private String code;
    private String message;
    private Object data;

    public static ErrorData business(String code, String message) {
        return ErrorData.builder()
                .type("BUSINESS")
                .code(code)
                .message(message)
                .build();
    }

    public static ErrorData business(String code, String message, Object data) {
        return ErrorData.builder()
                .type("BUSINESS")
                .code(code)
                .message(message)
                .data(data)
                .build();
    }

    public static ErrorData field(String code, String message, Object data) {
        return ErrorData.builder()
                .type("FIELD")
                .code(code)
                .message(message)
                .data(data)
                .build();
    }
}
