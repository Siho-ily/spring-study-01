package com.sihoily.tilboard.global.exception.security;

import com.sihoily.tilboard.global.exception.common.BusinessException;
import com.sihoily.tilboard.global.exception.errorCode.ErrorCode;

public class InvalidTokenException extends BusinessException {
    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
