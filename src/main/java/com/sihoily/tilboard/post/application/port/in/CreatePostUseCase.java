package com.sihoily.tilboard.post.application.port.in;

import com.sihoily.tilboard.post.domain.Post;

import java.util.List;

public interface CreatePostUseCase {
    Post createPost(String authorId, String title, String content, List<String> tagNames);
}
