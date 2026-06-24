package com.sihoily.tilboard.post.application.port.out;

import com.sihoily.tilboard.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LoadPostPort {
    Optional<Post> loadPost(Long id);
    Page<Post> loadPosts(String keyword, Pageable pageable);
}
