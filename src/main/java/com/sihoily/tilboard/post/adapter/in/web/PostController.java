package com.sihoily.tilboard.post.adapter.in.web;

import com.sihoily.tilboard.global.response.ApiResponse;
import com.sihoily.tilboard.global.response.PageResponse;
import com.sihoily.tilboard.global.security.CustomUserDetails;
import com.sihoily.tilboard.post.adapter.in.web.dto.request.CreatePostRequest;
import com.sihoily.tilboard.post.adapter.in.web.dto.request.UpdatePostRequest;
import com.sihoily.tilboard.post.adapter.in.web.dto.response.PostResponse;
import com.sihoily.tilboard.post.adapter.in.web.dto.response.PostSummaryResponse;
import com.sihoily.tilboard.post.application.port.in.CreatePostUseCase;
import com.sihoily.tilboard.post.application.port.in.DeletePostUseCase;
import com.sihoily.tilboard.post.application.port.in.GetPostUseCase;
import com.sihoily.tilboard.post.application.port.in.UpdatePostUseCase;
import com.sihoily.tilboard.post.domain.Post;
import com.sihoily.tilboard.tag.application.port.in.GetTagsUseCase;
import com.sihoily.tilboard.tag.domain.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/til")
@RequiredArgsConstructor
public class PostController implements PostControllerDocs {

    private final CreatePostUseCase createPostUseCase;
    private final GetPostUseCase getPostUseCase;
    private final UpdatePostUseCase updatePostUseCase;
    private final DeletePostUseCase deletePostUseCase;
    private final GetTagsUseCase getTagsUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<PostResponse>> createPost(
            @Valid @RequestBody CreatePostRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Post post = createPostUseCase.createPost(userDetails.getUserId(), request.getTitle(), request.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(PostResponse.from(post, List.of())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> getPost(@PathVariable Long id) {
        Post post = getPostUseCase.getPost(id);
        List<Tag> tags = getTagsUseCase.getTags(id);
        return ResponseEntity.ok(ApiResponse.success(PostResponse.from(post, tags)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<PostSummaryResponse>>> getPosts(
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Post> posts = getPostUseCase.getPosts(keyword, pageable);
        return ResponseEntity.ok(ApiResponse.success(PageResponse.from(posts.map(PostSummaryResponse::from))));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePostRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Post post = updatePostUseCase.updatePost(id, userDetails.getUserId(), request.getTitle(), request.getContent());
        List<Tag> tags = getTagsUseCase.getTags(id);
        return ResponseEntity.ok(ApiResponse.success(PostResponse.from(post, tags)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        deletePostUseCase.deletePost(id, userDetails.getUserId());
        return ResponseEntity.noContent().build();
    }
}
