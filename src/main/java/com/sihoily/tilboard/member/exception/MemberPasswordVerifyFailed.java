package com.sihoily.tilboard.member.exception;

import com.sihoily.tilboard.global.exception.common.BusinessException;
import com.sihoily.tilboard.global.exception.error.ErrorCode;
import com.sihoily.tilboard.global.exception.error.ErrorData;

public class MemberPasswordVerifyFailed extends BusinessException {
    public MemberPasswordVerifyFailed() {
        super(ErrorCode.AUTHORIZATION_FAILED, ErrorData.of("아이디나 비밀번호가 일치하지 않습니다."));
    }
}
