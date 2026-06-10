package com.sihoily.tilboard.global.exception.security;

import com.sihoily.tilboard.global.exception.common.BusinessException;
import com.sihoily.tilboard.global.exception.errorCode.ErrorCode;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException(String message) {
        super(ErrorCode.UNAUTHORIZED);
    }
}
