package com.sihoily.tilboard.tag.adapter.in.web;

import com.sihoily.tilboard.global.response.ApiResponse;
import com.sihoily.tilboard.global.security.CustomUserDetails;
import com.sihoily.tilboard.tag.adapter.in.web.dto.request.AddTagRequest;
import com.sihoily.tilboard.tag.adapter.in.web.dto.response.TagResponse;
import com.sihoily.tilboard.tag.application.port.in.AddTagUseCase;
import com.sihoily.tilboard.tag.application.port.in.DeleteTagUseCase;
import com.sihoily.tilboard.tag.application.port.in.GetTagsUseCase;
import com.sihoily.tilboard.tag.domain.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/til/{postId}/tags")
@RequiredArgsConstructor
public class TagController {

    private final AddTagUseCase addTagUseCase;
    private final DeleteTagUseCase deleteTagUseCase;
    private final GetTagsUseCase getTagsUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<TagResponse>> addTag(
            @PathVariable Long postId,
            @Valid @RequestBody AddTagRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Tag tag = addTagUseCase.addTag(postId, userDetails.getUserId(), request.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(TagResponse.from(tag)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TagResponse>>> getTags(@PathVariable Long postId) {
        List<TagResponse> tags = getTagsUseCase.getTags(postId).stream().map(TagResponse::from).toList();
        return ResponseEntity.ok(ApiResponse.success(tags));
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<Void> deleteTag(
            @PathVariable Long postId,
            @PathVariable Long tagId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        deleteTagUseCase.deleteTag(postId, tagId, userDetails.getUserId());
        return ResponseEntity.noContent().build();
    }
}
