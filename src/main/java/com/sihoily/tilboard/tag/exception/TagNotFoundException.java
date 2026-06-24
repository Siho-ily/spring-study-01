package com.sihoily.tilboard.tag.exception;

import com.sihoily.tilboard.global.exception.common.BusinessException;
import com.sihoily.tilboard.global.exception.error.ErrorCode;
import com.sihoily.tilboard.global.exception.error.ErrorData;

public class TagNotFoundException extends BusinessException {
    public TagNotFoundException(Long id) {
        super(ErrorCode.TAG_NOT_FOUND,
                ErrorData.field("태그를 찾을 수 없습니다.", "id", id));
    }
}
