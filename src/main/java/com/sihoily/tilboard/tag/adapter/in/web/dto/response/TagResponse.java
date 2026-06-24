package com.sihoily.tilboard.tag.adapter.in.web.dto.response;

import com.sihoily.tilboard.tag.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TagResponse {
    private Long id;
    private String name;
    private LocalDateTime createdAt;

    public static TagResponse from(Tag tag) {
        return new TagResponse(tag.id(), tag.name(), tag.createdAt());
    }
}
