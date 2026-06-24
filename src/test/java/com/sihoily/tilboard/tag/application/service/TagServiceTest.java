package com.sihoily.tilboard.tag.application.service;

import com.sihoily.tilboard.tag.application.port.out.LoadTagPort;
import com.sihoily.tilboard.tag.domain.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock private LoadTagPort loadTagPort;

    @InjectMocks
    private TagService tagService;

    @Nested
    @DisplayName("게시글 태그 조회")
    class GetTags {

        @Test
        @DisplayName("성공 - 게시글의 태그 목록을 반환한다")
        void success() {
            // Given
            List<Tag> tags = List.of(new Tag(1L, "Spring"), new Tag(2L, "JPA"));
            when(loadTagPort.loadTagsByPostId(1L)).thenReturn(tags);

            // When
            List<Tag> result = tagService.getTags(1L);

            // Then
            assertThat(result).hasSize(2);
            assertThat(result.get(0).name()).isEqualTo("Spring");
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

    @Nested
    @DisplayName("게시글 목록 태그 일괄 조회")
    class GetTagsGroupedByPostIds {

        @Test
        @DisplayName("성공 - postId별로 그룹화된 태그 맵을 반환한다")
        void success() {
            // Given
            Map<Long, List<Tag>> tagMap = Map.of(
                    1L, List.of(new Tag(1L, "Spring")),
                    2L, List.of(new Tag(2L, "JPA"), new Tag(3L, "QueryDSL"))
            );
            when(loadTagPort.loadTagsGroupedByPostIds(List.of(1L, 2L))).thenReturn(tagMap);

            // When
            Map<Long, List<Tag>> result = tagService.getTagsGroupedByPostIds(List.of(1L, 2L));

            // Then
            assertThat(result.get(1L)).hasSize(1);
            assertThat(result.get(2L)).hasSize(2);
        }
    }

    @Nested
    @DisplayName("전체 태그 목록 조회 (사용 빈도순)")
    class GetAllTags {

        @Test
        @DisplayName("성공 - 사용 빈도순으로 태그 목록을 반환한다")
        void success() {
            // Given
            List<Tag> tags = List.of(new Tag(1L, "Spring"), new Tag(2L, "JPA"), new Tag(3L, "JWT"));
            when(loadTagPort.loadAllByUsageCount()).thenReturn(tags);

            // When
            List<Tag> result = tagService.getAllTags();

            // Then
            assertThat(result).hasSize(3);
            verify(loadTagPort).loadAllByUsageCount();
        }

        @Test
        @DisplayName("성공 - 태그가 없으면 빈 리스트를 반환한다")
        void successWithEmpty() {
            // Given
            when(loadTagPort.loadAllByUsageCount()).thenReturn(List.of());

            // When
            List<Tag> result = tagService.getAllTags();

            // Then
            assertThat(result).isEmpty();
        }
    }
}
