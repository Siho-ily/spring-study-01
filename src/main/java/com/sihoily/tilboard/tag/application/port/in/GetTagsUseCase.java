package com.sihoily.tilboard.tag.application.port.in;

import com.sihoily.tilboard.tag.domain.Tag;

import java.util.List;
import java.util.Map;

public interface GetTagsUseCase {
    List<Tag> getTags(Long postId);
    Map<Long, List<Tag>> getTagsGroupedByPostIds(List<Long> postIds);
}
