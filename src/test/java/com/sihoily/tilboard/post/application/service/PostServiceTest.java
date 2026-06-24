package com.sihoily.tilboard.post.application.service;

import com.sihoily.tilboard.post.application.port.out.DeletePostPort;
import com.sihoily.tilboard.post.application.port.out.IncrementViewCountPort;
import com.sihoily.tilboard.post.application.port.out.LoadPostPort;
import com.sihoily.tilboard.post.application.port.out.SavePostPort;
import com.sihoily.tilboard.post.domain.Post;
import com.sihoily.tilboard.post.exception.PostAccessDeniedException;
import com.sihoily.tilboard.post.exception.PostNotFoundException;
import com.sihoily.tilboard.tag.application.port.out.LoadTagPort;
import com.sihoily.tilboard.tag.application.port.out.SavePostTagPort;
import com.sihoily.tilboard.tag.application.port.out.SaveTagPort;
import com.sihoily.tilboard.tag.domain.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock private SavePostPort savePostPort;
    @Mock private LoadPostPort loadPostPort;
    @Mock private DeletePostPort deletePostPort;
    @Mock private IncrementViewCountPort incrementViewCountPort;
    @Mock private LoadTagPort loadTagPort;
    @Mock private SaveTagPort saveTagPort;
    @Mock private SavePostTagPort savePostTagPort;

    @InjectMocks
    private PostService postService;

    private Post makePost(Long id, String authorId) {
        return new Post(id, "제목", "내용", authorId, 0, LocalDateTime.now(), null, null);
    }

    @Nested
    @DisplayName("게시글 작성")
    class CreatePost {

        @Test
        @DisplayName("성공 - 태그 없이 작성")
        void successWithoutTags() {
            // Given
            Post saved = makePost(1L, "user1");
            when(savePostPort.savePost(any())).thenReturn(saved);

            // When
            Post result = postService.createPost("user1", "제목", "내용", null);

            // Then
            assertThat(result.id()).isEqualTo(1L);
            verify(savePostPort).savePost(any());
            verify(savePostTagPort, never()).savePostTags(any(), any());
        }

        @Test
        @DisplayName("성공 - 태그 포함 작성 시 태그가 생성·연결된다")
        void successWithTags() {
            // Given
            Post saved = makePost(1L, "user1");
            Tag tag = new Tag(10L, "Spring");
            when(savePostPort.savePost(any())).thenReturn(saved);
            when(loadTagPort.findByName("Spring")).thenReturn(Optional.empty());
            when(saveTagPort.saveTag(any())).thenReturn(tag);

            // When
            postService.createPost("user1", "제목", "내용", List.of("Spring"));

            // Then
            verify(saveTagPort).saveTag(any());
            verify(savePostTagPort).savePostTags(1L, List.of(10L));
        }

        @Test
        @DisplayName("성공 - 기존 태그는 새로 생성하지 않고 재사용한다")
        void successReuseExistingTag() {
            // Given
            Post saved = makePost(1L, "user1");
            Tag existing = new Tag(10L, "Spring");
            when(savePostPort.savePost(any())).thenReturn(saved);
            when(loadTagPort.findByName("Spring")).thenReturn(Optional.of(existing));

            // When
            postService.createPost("user1", "제목", "내용", List.of("Spring"));

            // Then
            verify(saveTagPort, never()).saveTag(any());
            verify(savePostTagPort).savePostTags(1L, List.of(10L));
        }
    }

    @Nested
    @DisplayName("게시글 단건 조회")
    class GetPost {

        @Test
        @DisplayName("성공 - 조회 시 viewCount가 증가한다")
        void success() {
            // Given
            Post post = makePost(1L, "user1");
            when(loadPostPort.loadPost(1L)).thenReturn(Optional.of(post));

            // When
            Post result = postService.getPost(1L);

            // Then
            assertThat(result.id()).isEqualTo(1L);
            verify(incrementViewCountPort).incrementViewCount(1L);
        }

        @Test
        @DisplayName("실패 - 존재하지 않는 게시글이면 PostNotFoundException 발생")
        void failWhenPostNotFound() {
            // Given
            when(loadPostPort.loadPost(999L)).thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> postService.getPost(999L))
                    .isInstanceOf(PostNotFoundException.class);

            verify(incrementViewCountPort, never()).incrementViewCount(any());
        }
    }

    @Nested
    @DisplayName("게시글 수정")
    class UpdatePost {

        @Test
        @DisplayName("성공")
        void success() {
            // Given
            Post post = makePost(1L, "user1");
            Post updated = new Post(1L, "수정 제목", "수정 내용", "user1", 0, post.createdAt(), LocalDateTime.now(), null);
            when(loadPostPort.loadPost(1L)).thenReturn(Optional.of(post));
            when(savePostPort.savePost(any())).thenReturn(updated);

            // When
            Post result = postService.updatePost(1L, "user1", "수정 제목", "수정 내용", null);

            // Then
            assertThat(result.title()).isEqualTo("수정 제목");
            assertThat(result.updatedAt()).isNotNull();
            verify(savePostPort).savePost(any());
        }

        @Test
        @DisplayName("실패 - 존재하지 않는 게시글이면 PostNotFoundException 발생")
        void failWhenPostNotFound() {
            // Given
            when(loadPostPort.loadPost(999L)).thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> postService.updatePost(999L, "user1", "제목", "내용", null))
                    .isInstanceOf(PostNotFoundException.class);

            verify(savePostPort, never()).savePost(any());
        }

        @Test
        @DisplayName("실패 - 작성자가 아니면 PostAccessDeniedException 발생")
        void failWhenNotAuthor() {
            // Given
            Post post = makePost(1L, "user1");
            when(loadPostPort.loadPost(1L)).thenReturn(Optional.of(post));

            // When & Then
            assertThatThrownBy(() -> postService.updatePost(1L, "other", "제목", "내용", null))
                    .isInstanceOf(PostAccessDeniedException.class);

            verify(savePostPort, never()).savePost(any());
        }
    }

    @Nested
    @DisplayName("게시글 삭제")
    class DeletePost {

        @Test
        @DisplayName("성공")
        void success() {
            // Given
            Post post = makePost(1L, "user1");
            when(loadPostPort.loadPost(1L)).thenReturn(Optional.of(post));

            // When
            postService.deletePost(1L, "user1");

            // Then
            verify(deletePostPort).deletePost(1L);
        }

        @Test
        @DisplayName("실패 - 존재하지 않는 게시글이면 PostNotFoundException 발생")
        void failWhenPostNotFound() {
            // Given
            when(loadPostPort.loadPost(999L)).thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> postService.deletePost(999L, "user1"))
                    .isInstanceOf(PostNotFoundException.class);

            verify(deletePostPort, never()).deletePost(any());
        }

        @Test
        @DisplayName("실패 - 작성자가 아니면 PostAccessDeniedException 발생")
        void failWhenNotAuthor() {
            // Given
            Post post = makePost(1L, "user1");
            when(loadPostPort.loadPost(1L)).thenReturn(Optional.of(post));

            // When & Then
            assertThatThrownBy(() -> postService.deletePost(1L, "other"))
                    .isInstanceOf(PostAccessDeniedException.class);

            verify(deletePostPort, never()).deletePost(any());
        }
    }
}
