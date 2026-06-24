package com.sihoily.tilboard.post.application.port.out;

import com.sihoily.tilboard.post.domain.Post;

public interface SavePostPort {
    Post savePost(Post post);
}
