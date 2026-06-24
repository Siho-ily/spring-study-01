package com.sihoily.tilboard.post.exception;

import com.sihoily.tilboard.global.exception.common.BusinessException;
import com.sihoily.tilboard.global.exception.error.ErrorCode;
import com.sihoily.tilboard.global.exception.error.ErrorData;

public class PostNotFoundException extends BusinessException {
    public PostNotFoundException(Long id) {
        super(ErrorCode.POST_NOT_FOUND,
                ErrorData.field("게시글을 찾을 수 없습니다.", "id", id));
    }
}
