package com.sihoily.tilboard.post.domain;

import java.time.LocalDateTime;

public record Post(
        Long id,
        String title,
        String content,
        String authorId,
        int viewCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt
) {}
