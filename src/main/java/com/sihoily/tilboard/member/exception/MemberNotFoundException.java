package com.sihoily.tilboard.member.exception;

import com.sihoily.tilboard.global.exception.common.BusinessException;
import com.sihoily.tilboard.global.exception.errorCode.ErrorCode;

import java.text.MessageFormat;

public class MemberNotFoundException extends BusinessException {
    public MemberNotFoundException(String userId) {
        super(ErrorCode.MEMBER_NOT_FOUND, MessageFormat.format("회원 정보를 찾을 수 없습니다.(userId: {0})", userId));
    }
}
