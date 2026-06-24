package com.sihoily.tilboard.tag.domain;

import java.time.LocalDateTime;

public record Tag(
        Long id,
        String name,
        Long postId,
        LocalDateTime createdAt
) {}
