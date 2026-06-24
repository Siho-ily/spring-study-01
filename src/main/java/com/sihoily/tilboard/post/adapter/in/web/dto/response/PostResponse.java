package com.sihoily.tilboard.post.adapter.in.web.dto.response;

import com.sihoily.tilboard.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String authorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PostResponse from(Post post) {
        return new PostResponse(
                post.id(), post.title(), post.content(),
                post.authorId(), post.createdAt(), post.updatedAt()
        );
    }
}
