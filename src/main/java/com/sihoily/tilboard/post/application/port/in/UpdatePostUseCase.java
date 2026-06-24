package com.sihoily.tilboard.post.application.port.in;

import com.sihoily.tilboard.post.domain.Post;

public interface UpdatePostUseCase {
    Post updatePost(Long id, String requesterId, String title, String content);
}
