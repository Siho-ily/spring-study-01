package com.sihoily.tilboard.member.exception;

import com.sihoily.tilboard.global.exception.common.BusinessException;
import com.sihoily.tilboard.global.exception.error.ErrorCode;
import com.sihoily.tilboard.global.exception.error.ErrorData;

import java.util.List;

public class MemberConflictException extends BusinessException {
    public MemberConflictException(List<ErrorData> errors) {
        super(ErrorCode.OCCUPIED_REQUEST_PARAMETER, "중복된 회원 정보가 있습니다.", errors);
    }
}
