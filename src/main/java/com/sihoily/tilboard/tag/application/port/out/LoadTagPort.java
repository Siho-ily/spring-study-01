package com.sihoily.tilboard.tag.application.port.out;

import com.sihoily.tilboard.tag.domain.Tag;

import java.util.List;
import java.util.Optional;

public interface LoadTagPort {
    Optional<Tag> loadTag(Long tagId);
    List<Tag> loadTagsByPostId(Long postId);
}
