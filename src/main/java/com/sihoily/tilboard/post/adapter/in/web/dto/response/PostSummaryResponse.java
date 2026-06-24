package com.sihoily.tilboard.post.adapter.in.web.dto.response;

import com.sihoily.tilboard.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostSummaryResponse {
    private Long id;
    private String title;
    private String authorId;
    private int viewCount;
    private LocalDateTime createdAt;

    public static PostSummaryResponse from(Post post) {
        return new PostSummaryResponse(post.id(), post.title(), post.authorId(), post.viewCount(), post.createdAt());
    }
}
