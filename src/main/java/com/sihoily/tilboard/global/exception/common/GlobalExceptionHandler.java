package com.sihoily.tilboard.global.exception.common;

import com.sihoily.tilboard.global.exception.errorCode.ErrorCode;
import com.sihoily.tilboard.global.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // =================================
    // * 기타 예외 (가능한 이쪽까진 안와야 함) *
    // =================================
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleException(BusinessException e) {
        log.error(e.getMessage(), e);

        if (e.hasErrors()) {
            return ResponseEntity
                    .status(e.getErrorCode().getStatus())
                    .body(ApiResponse.error(e.getResponseMessage(), e.getErrors()));
        }

        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ApiResponse.error(e.getErrorCode(), e.getResponseMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        log.error(e.getMessage(), e);
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResponse.error(errorCode));
    }
}
