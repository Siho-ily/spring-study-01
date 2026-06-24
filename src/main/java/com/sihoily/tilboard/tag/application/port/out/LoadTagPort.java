package com.sihoily.tilboard.tag.application.port.out;

import com.sihoily.tilboard.tag.domain.Tag;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LoadTagPort {
    Optional<Tag> findByName(String name);
    List<Tag> loadTagsByPostId(Long postId);
    Map<Long, List<Tag>> loadTagsGroupedByPostIds(List<Long> postIds);
    List<Tag> loadAllByUsageCount();
}
