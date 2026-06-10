package com.sihoily.tilboard.member.exception;

import com.sihoily.tilboard.global.exception.common.BusinessException;
import com.sihoily.tilboard.global.exception.error.ErrorData;
import org.springframework.http.HttpStatus;

import java.util.List;

public class MemberConflictException extends BusinessException {
    public MemberConflictException(List<ErrorData> errors) {
        super(HttpStatus.CONFLICT, "중복된 회원 정보가 있습니다.", errors);
    }
}
