package com.sihoily.tilboard.member.exception;

import com.sihoily.tilboard.global.exception.common.BusinessException;
import com.sihoily.tilboard.global.exception.error.ErrorCode;
import com.sihoily.tilboard.global.exception.error.ErrorData;

public class MemberNotFoundException extends BusinessException {
    public MemberNotFoundException(String userId) {
        super(ErrorCode.MEMBER_NOT_FOUND,
                ErrorData.field("사용자 정보를 찾을 수 없습니다.", "userId", userId)
        );
    }
}
