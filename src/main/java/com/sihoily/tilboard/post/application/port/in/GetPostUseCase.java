package com.sihoily.tilboard.post.application.port.in;

import com.sihoily.tilboard.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetPostUseCase {
    Post getPost(Long id);
    Page<Post> getPosts(String keyword, Pageable pageable);
}
