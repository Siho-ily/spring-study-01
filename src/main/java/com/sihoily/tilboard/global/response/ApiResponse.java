package com.sihoily.tilboard.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sihoily.tilboard.global.exception.error.ErrorData;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private List<ErrorData> errors;

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "success", data, null);
    }
    public static ApiResponse<Void> error(String message, ErrorData error) {
        return new ApiResponse<>(false, message, null, List.of(error));
    }

    public static ApiResponse<Void> error(String message, List<ErrorData> errors) {
        return new ApiResponse<>(false, message, null, errors.isEmpty() ? null : errors);
    }
}
