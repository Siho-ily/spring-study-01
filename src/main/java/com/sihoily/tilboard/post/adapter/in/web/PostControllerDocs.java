package com.sihoily.tilboard.post.adapter.in.web;

import com.sihoily.tilboard.global.response.ApiResponse;
import com.sihoily.tilboard.global.response.PageResponse;
import com.sihoily.tilboard.global.security.CustomUserDetails;
import com.sihoily.tilboard.post.adapter.in.web.dto.request.CreatePostRequest;
import com.sihoily.tilboard.post.adapter.in.web.dto.request.UpdatePostRequest;
import com.sihoily.tilboard.post.adapter.in.web.dto.response.PostResponse;
import com.sihoily.tilboard.post.adapter.in.web.dto.response.PostSummaryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "TIL 게시글", description = "TIL 게시글 관련 API")
public interface PostControllerDocs {

    @Operation(summary = "게시글 작성", description = "TIL 게시글을 작성합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "게시글 작성 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostResponse.class),
                            examples = @ExampleObject(name = "게시글 작성 성공", value = """
                                    {
                                      "success": true,
                                      "message": "success",
                                      "data": {
                                        "id": 1,
                                        "title": "오늘 배운 Spring Security",
                                        "content": "JWT 필터를 공부했다.",
                                        "authorId": "myUser123",
                                        "createdAt": "2026-06-24T10:00:00",
                                        "updatedAt": null
                                      }
                                    }
                                    """))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "입력값 검증 실패",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(name = "입력값 검증 실패", value = """
                                    {
                                      "success": false,
                                      "message": "요청 필드 검증에 실패하였습니다.",
                                      "errors": [{ "message": "빈 문자열은 허용되지 않습니다.", "data": { "key": "title", "value": "" } }]
                                    }
                                    """))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 필요",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(name = "인증 필요", value = """
                                    { "success": false, "message": "로그인이 필요한 리소스입니다." }
                                    """)))
    })
    ResponseEntity<ApiResponse<PostResponse>> createPost(CreatePostRequest request, CustomUserDetails userDetails);

    @Operation(summary = "게시글 단건 조회", description = "게시글 ID로 단건 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostResponse.class),
                            examples = @ExampleObject(name = "조회 성공", value = """
                                    {
                                      "success": true,
                                      "message": "success",
                                      "data": {
                                        "id": 1,
                                        "title": "오늘 배운 Spring Security",
                                        "content": "JWT 필터를 공부했다.",
                                        "authorId": "myUser123",
                                        "createdAt": "2026-06-24T10:00:00",
                                        "updatedAt": null
                                      }
                                    }
                                    """))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글 없음",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(name = "게시글 없음", value = """
                                    { "success": false, "message": "게시글을 찾을 수 없습니다.", "errors": [{ "message": "게시글을 찾을 수 없습니다.", "data": { "key": "id", "value": 999 } }] }
                                    """)))
    })
    ResponseEntity<ApiResponse<PostResponse>> getPost(@PathVariable Long id);

    @Operation(summary = "게시글 목록 조회", description = "전체 게시글 목록을 페이지네이션으로 조회합니다. keyword로 제목 검색, page/size로 페이지 지정이 가능합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(name = "목록 조회 성공", value = """
                                    {
                                      "success": true,
                                      "message": "success",
                                      "data": {
                                        "content": [
                                          { "id": 2, "title": "QueryDSL 정리", "authorId": "myUser123", "createdAt": "2026-06-24T11:00:00" },
                                          { "id": 1, "title": "오늘 배운 Spring Security", "authorId": "myUser123", "createdAt": "2026-06-24T10:00:00" }
                                        ],
                                        "page": 0,
                                        "size": 10,
                                        "totalElements": 25,
                                        "totalPages": 3,
                                        "hasNext": true,
                                        "hasPrevious": false
                                      }
                                    }
                                    """)))
    })
    ResponseEntity<ApiResponse<PageResponse<PostSummaryResponse>>> getPosts(
            @RequestParam(required = false) String keyword, Pageable pageable);

    @Operation(summary = "게시글 수정", description = "본인 게시글을 수정합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostResponse.class),
                            examples = @ExampleObject(name = "수정 성공", value = """
                                    {
                                      "success": true,
                                      "message": "success",
                                      "data": {
                                        "id": 1,
                                        "title": "수정된 제목",
                                        "content": "수정된 내용",
                                        "authorId": "myUser123",
                                        "createdAt": "2026-06-24T10:00:00",
                                        "updatedAt": "2026-06-24T12:00:00"
                                      }
                                    }
                                    """))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "권한 없음",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(name = "권한 없음", value = """
                                    { "success": false, "message": "게시글에 대한 권한이 없습니다." }
                                    """))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글 없음",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(name = "게시글 없음", value = """
                                    { "success": false, "message": "게시글을 찾을 수 없습니다." }
                                    """)))
    })
    ResponseEntity<ApiResponse<PostResponse>> updatePost(
            @PathVariable Long id, UpdatePostRequest request, CustomUserDetails userDetails);

    @Operation(summary = "게시글 삭제", description = "본인 게시글을 삭제합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "삭제 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "권한 없음",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(name = "권한 없음", value = """
                                    { "success": false, "message": "게시글에 대한 권한이 없습니다." }
                                    """))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글 없음",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(name = "게시글 없음", value = """
                                    { "success": false, "message": "게시글을 찾을 수 없습니다." }
                                    """)))
    })
    ResponseEntity<Void> deletePost(@PathVariable Long id, CustomUserDetails userDetails);
}
