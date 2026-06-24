package com.sihoily.tilboard.tag.application.port.out;

import java.util.List;

public interface SavePostTagPort {
    void savePostTags(Long postId, List<Long> tagIds);
    void deletePostTagsByPostId(Long postId);
}
