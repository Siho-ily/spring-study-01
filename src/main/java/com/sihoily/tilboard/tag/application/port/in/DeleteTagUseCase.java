package com.sihoily.tilboard.tag.application.port.in;

public interface DeleteTagUseCase {
    void deleteTag(Long postId, Long tagId, String requesterId);
}
