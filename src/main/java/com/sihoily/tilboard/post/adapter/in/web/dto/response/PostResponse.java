package com.sihoily.tilboard.post.adapter.in.web.dto.response;

import com.sihoily.tilboard.post.domain.Post;
import com.sihoily.tilboard.tag.adapter.in.web.dto.response.TagResponse;
import com.sihoily.tilboard.tag.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String authorId;
    private int viewCount;
    private List<TagResponse> tags;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PostResponse from(Post post, List<Tag> tags) {
        return new PostResponse(
                post.id(), post.title(), post.content(),
                post.authorId(), post.viewCount(),
                tags.stream().map(TagResponse::from).toList(),
                post.createdAt(), post.updatedAt()
        );
    }
}
