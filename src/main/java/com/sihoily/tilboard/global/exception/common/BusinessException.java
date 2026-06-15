package com.sihoily.tilboard.global.exception.common;

import com.sihoily.tilboard.global.exception.error.ErrorCode;
import com.sihoily.tilboard.global.exception.error.ErrorData;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class BusinessException extends RuntimeException {
    private final HttpStatus status;
    private final String message;
    private final List<ErrorData> errors;


    // ===================
    //   * No ErrorData *
    // ===================

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
        this.errors = List.of();
    }

    // ===================
    //   * 1 ErrorData *
    // ===================

    public BusinessException(ErrorCode errorCode, ErrorData data) {
        super(errorCode.getMessage());
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
        this.errors = List.of(data);
    }

    public BusinessException(ErrorCode errorCode, String message, ErrorData data) {
        super(message);
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
        this.errors = List.of(data);
    }

    // ===================
    //  * ErrorDataList *
    // ===================

    public BusinessException(ErrorCode errorCode, List<ErrorData> data) {
        super(errorCode.getMessage());
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
        this.errors = data;
    }

    public BusinessException(ErrorCode errorCode, String message, List<ErrorData> data) {
        super(message);
        this.status = errorCode.getStatus();
        this.message = message;
        this.errors = data;
    }
}
