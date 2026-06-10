package com.sihoily.tilboard.member.exception;

import com.sihoily.tilboard.global.exception.common.BusinessException;
import com.sihoily.tilboard.global.exception.errorCode.ErrorCode;
import com.sihoily.tilboard.global.response.ErrorData;

import java.util.List;

public class MemberConflictException extends BusinessException {
    public MemberConflictException(List<ErrorData> errors) {
        super(ErrorCode.MEMBER_CONFLICT, errors);
    }
}
