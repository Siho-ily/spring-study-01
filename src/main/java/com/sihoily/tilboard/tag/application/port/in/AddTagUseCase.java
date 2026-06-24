package com.sihoily.tilboard.tag.application.port.in;

import com.sihoily.tilboard.tag.domain.Tag;

public interface AddTagUseCase {
    Tag addTag(Long postId, String requesterId, String name);
}
