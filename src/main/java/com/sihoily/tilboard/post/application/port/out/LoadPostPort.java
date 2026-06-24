package com.sihoily.tilboard.post.application.port.out;

import com.sihoily.tilboard.post.domain.Post;

import java.util.List;
import java.util.Optional;

public interface LoadPostPort {
    Optional<Post> loadPost(Long id);
    List<Post> loadPosts(String keyword);
}
