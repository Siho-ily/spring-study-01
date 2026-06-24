package com.sihoily.tilboard.tag.exception;

import com.sihoily.tilboard.global.exception.common.BusinessException;
import com.sihoily.tilboard.global.exception.error.ErrorCode;

public class TagAccessDeniedException extends BusinessException {
    public TagAccessDeniedException() {
        super(ErrorCode.TAG_ACCESS_DENIED);
    }
}
