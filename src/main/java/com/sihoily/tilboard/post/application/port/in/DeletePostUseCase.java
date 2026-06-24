package com.sihoily.tilboard.post.application.port.in;

public interface DeletePostUseCase {
    void deletePost(Long id, String requesterId);
}
