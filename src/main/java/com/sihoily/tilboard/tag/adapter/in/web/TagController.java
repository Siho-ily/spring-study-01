package com.sihoily.tilboard.tag.adapter.in.web;

import com.sihoily.tilboard.global.response.ApiResponse;
import com.sihoily.tilboard.tag.adapter.in.web.dto.response.TagResponse;
import com.sihoily.tilboard.tag.application.port.in.GetAllTagsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final GetAllTagsUseCase getAllTagsUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TagResponse>>> getAllTags() {
        List<TagResponse> tags = getAllTagsUseCase.getAllTags().stream()
                .map(TagResponse::from)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(tags));
    }
}
