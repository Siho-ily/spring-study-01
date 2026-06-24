package com.sihoily.tilboard.post.application.port.in;

import com.sihoily.tilboard.post.domain.Post;

import java.util.List;

public interface GetPostUseCase {
    Post getPost(Long id);
    List<Post> getPosts(String keyword);
}
