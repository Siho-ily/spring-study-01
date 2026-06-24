package com.sihoily.tilboard.post.application.port.in;

import com.sihoily.tilboard.post.domain.Post;

public interface CreatePostUseCase {
    Post createPost(String authorId, String title, String content);
}
