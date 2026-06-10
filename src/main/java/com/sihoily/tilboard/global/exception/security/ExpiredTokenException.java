package com.sihoily.tilboard.global.exception.security;

import com.sihoily.tilboard.global.exception.common.BusinessException;
import com.sihoily.tilboard.global.exception.errorCode.ErrorCode;

public class ExpiredTokenException extends BusinessException {
    public ExpiredTokenException() {
        super(ErrorCode.EXPIRED_TOKEN);
    }
}
