package com.sihoily.tilboard.post.exception;

import com.sihoily.tilboard.global.exception.common.BusinessException;
import com.sihoily.tilboard.global.exception.error.ErrorCode;

public class PostAccessDeniedException extends BusinessException {
    public PostAccessDeniedException() {
        super(ErrorCode.POST_ACCESS_DENIED);
    }
}
