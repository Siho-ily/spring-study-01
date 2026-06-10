package com.sihoily.tilboard.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private List<ErrorData> errors;

    public record ErrorData(String type, String field, String message){}

    public static <T> ApiResponse<T> success(T data){
        return new ApiResponse<>(true, "success", data, null);
    }

    public static <T> ApiResponse<T> success(String message, T data){
        return new ApiResponse<>(true, message, data, null);
    }

    public static <T> ApiResponse<T> error(String message){
        return new ApiResponse<>(false, message, null, null);
    }

    public static <T> ApiResponse<T> error(String message, List<ErrorData> errors){
        return new ApiResponse<>(false, message, null, errors);
    }
}
