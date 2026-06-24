package com.sihoily.tilboard.tag.application.service;

import com.sihoily.tilboard.post.application.port.out.LoadPostPort;
import com.sihoily.tilboard.post.domain.Post;
import com.sihoily.tilboard.post.exception.PostNotFoundException;
import com.sihoily.tilboard.tag.application.port.out.DeleteTagPort;
import com.sihoily.tilboard.tag.application.port.out.LoadTagPort;
import com.sihoily.tilboard.tag.application.port.out.SaveTagPort;
import com.sihoily.tilboard.tag.domain.Tag;
import com.sihoily.tilboard.tag.exception.TagAccessDeniedException;
import com.sihoily.tilboard.tag.exception.TagNotFoundException;
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
class TagServiceTest {

    @Mock private SaveTagPort saveTagPort;
    @Mock private LoadTagPort loadTagPort;
    @Mock private DeleteTagPort deleteTagPort;
    @Mock private LoadPostPort loadPostPort;

    @InjectMocks
    private TagService tagService;

    private Post makePost(Long id, String authorId) {
        return new Post(id, "제목", "내용", authorId, 0, LocalDateTime.now(), null, null);
    }

    private Tag makeTag(Long id, Long postId) {
        return new Tag(id, "Spring", postId, LocalDateTime.now());
    }

    @Nested
    @DisplayName("태그 추가")
    class AddTag {

        @Test
        @DisplayName("성공")
        void success() {
            // Given
            Post post = makePost(1L, "user1");
            Tag saved = makeTag(1L, 1L);
            when(loadPostPort.loadPost(1L)).thenReturn(Optional.of(post));
            when(saveTagPort.saveTag(any())).thenReturn(saved);

            // When
            Tag result = tagService.addTag(1L, "user1", "Spring");

            // Then
            assertThat(result.name()).isEqualTo("Spring");
            assertThat(result.postId()).isEqualTo(1L);
            verify(saveTagPort).saveTag(any());
        }

        @Test
        @DisplayName("실패 - 게시글이 존재하지 않으면 PostNotFoundException 발생")
        void failWhenPostNotFound() {
            // Given
            when(loadPostPort.loadPost(999L)).thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> tagService.addTag(999L, "user1", "Spring"))
                    .isInstanceOf(PostNotFoundException.class);

            verify(saveTagPort, never()).saveTag(any());
        }

        @Test
        @DisplayName("실패 - 작성자가 아니면 TagAccessDeniedException 발생")
        void failWhenNotAuthor() {
            // Given
            Post post = makePost(1L, "user1");
            when(loadPostPort.loadPost(1L)).thenReturn(Optional.of(post));

            // When & Then
            assertThatThrownBy(() -> tagService.addTag(1L, "other", "Spring"))
                    .isInstanceOf(TagAccessDeniedException.class);

            verify(saveTagPort, never()).saveTag(any());
        }
    }

    @Nested
    @DisplayName("태그 삭제")
    class DeleteTag {

        @Test
        @DisplayName("성공")
        void success() {
            // Given
            Post post = makePost(1L, "user1");
            Tag tag = makeTag(10L, 1L);
            when(loadPostPort.loadPost(1L)).thenReturn(Optional.of(post));
            when(loadTagPort.loadTag(10L)).thenReturn(Optional.of(tag));

            // When
            tagService.deleteTag(1L, 10L, "user1");

            // Then
            verify(deleteTagPort).deleteTag(10L);
        }

        @Test
        @DisplayName("실패 - 게시글이 존재하지 않으면 PostNotFoundException 발생")
        void failWhenPostNotFound() {
            // Given
            when(loadPostPort.loadPost(999L)).thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> tagService.deleteTag(999L, 10L, "user1"))
                    .isInstanceOf(PostNotFoundException.class);

            verify(deleteTagPort, never()).deleteTag(any());
        }

        @Test
        @DisplayName("실패 - 작성자가 아니면 TagAccessDeniedException 발생")
        void failWhenNotAuthor() {
            // Given
            Post post = makePost(1L, "user1");
            when(loadPostPort.loadPost(1L)).thenReturn(Optional.of(post));

            // When & Then
            assertThatThrownBy(() -> tagService.deleteTag(1L, 10L, "other"))
                    .isInstanceOf(TagAccessDeniedException.class);

            verify(deleteTagPort, never()).deleteTag(any());
        }

        @Test
        @DisplayName("실패 - 태그가 존재하지 않으면 TagNotFoundException 발생")
        void failWhenTagNotFound() {
            // Given
            Post post = makePost(1L, "user1");
            when(loadPostPort.loadPost(1L)).thenReturn(Optional.of(post));
            when(loadTagPort.loadTag(999L)).thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> tagService.deleteTag(1L, 999L, "user1"))
                    .isInstanceOf(TagNotFoundException.class);

            verify(deleteTagPort, never()).deleteTag(any());
        }
    }

    @Nested
    @DisplayName("태그 목록 조회")
    class GetTags {

        @Test
        @DisplayName("성공 - 게시글의 태그 목록을 반환한다")
        void success() {
            // Given
            List<Tag> tags = List.of(makeTag(1L, 1L), makeTag(2L, 1L));
            when(loadTagPort.loadTagsByPostId(1L)).thenReturn(tags);

            // When
            List<Tag> result = tagService.getTags(1L);

            // Then
            assertThat(result).hasSize(2);
            verify(loadTagPort).loadTagsByPostId(1L);
        }

        @Test
        @DisplayName("성공 - 태그가 없으면 빈 리스트를 반환한다")
        void successWithEmptyList() {
            // Given
            when(loadTagPort.loadTagsByPostId(1L)).thenReturn(List.of());

            // When
            List<Tag> result = tagService.getTags(1L);

            // Then
            assertThat(result).isEmpty();
        }
    }
}
