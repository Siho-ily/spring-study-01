package com.sihoily.tilboard.tag.application.port.in;

import com.sihoily.tilboard.tag.domain.Tag;

import java.util.List;

public interface GetTagsUseCase {
    List<Tag> getTags(Long postId);
}
