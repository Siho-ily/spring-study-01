package com.sihoily.tilboard.global.exception.common;

import com.sihoily.tilboard.global.exception.error.ErrorCode;
import com.sihoily.tilboard.global.exception.error.ErrorData;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;
    private final HttpStatus status;
    private final String responseMessage;
    private final List<ErrorData> errors;

    public BusinessException(ErrorCode errorCode) {
        super("["+ errorCode.getCode()+"] " + errorCode.getMessage());
        this.errorCode = errorCode;
        this.status = errorCode.getStatus();
        this.responseMessage = errorCode.getMessage();
        this.errors = null;
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super("["+ errorCode.getCode()+"] " + message);
        this.errorCode = errorCode;
        this.status = errorCode.getStatus();
        this.responseMessage = message;
        this.errors = null;
    }

    public BusinessException(ErrorCode errorCode, List<ErrorData> errors) {
        super("["+ errorCode.getCode()+"] " + errorCode.getMessage());
        this.errorCode = errorCode;
        this.status = errorCode.getStatus();
        this.responseMessage = errorCode.getMessage();
        this.errors = errors;
    }

    public BusinessException(HttpStatus status, String message, List<ErrorData> errors) {
        super(message);
        this.errorCode = null;
        this.status = status;
        this.responseMessage = message;
        this.errors = errors;
    }

    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }
}
