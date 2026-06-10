package com.sihoily.tilboard.global.exception.common;

import com.sihoily.tilboard.global.exception.errorCode.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super("["+ errorCode.getCode()+"] " + errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super("["+ errorCode.getCode()+"] " + errorCode.getMessage()+"(" + message + ")");
        this.errorCode = errorCode;
    }
}
