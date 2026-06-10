package com.sihoily.tilboard.member.domain;

import java.time.LocalDateTime;

public record Member
(
        Long id,
        String userId,
        String email,
        String password,
        String nickname,
        Role role,
        LocalDateTime createdAt,
        LocalDateTime deletedAt
) { }
