package com.sihoily.tilboard.tag.adapter.out.persistence.jpa;

import com.sihoily.tilboard.tag.application.port.out.LoadTagPort;
import com.sihoily.tilboard.tag.application.port.out.SavePostTagPort;
import com.sihoily.tilboard.tag.application.port.out.SaveTagPort;
import com.sihoily.tilboard.tag.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TagJpaPersistenceAdapter implements SaveTagPort, LoadTagPort, SavePostTagPort {

    private final TagJpaRepository tagRepository;
    private final PostTagJpaRepository postTagRepository;
    private final TagJpaMapper mapper;

    @Override
    public Tag saveTag(Tag tag) {
        return mapper.toDomain(tagRepository.save(mapper.toEntity(tag)));
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return tagRepository.findByName(name).map(mapper::toDomain);
    }

    @Override
    public List<Tag> loadTagsByPostId(Long postId) {
        List<Long> tagIds = postTagRepository.findByPostId(postId).stream()
                .map(PostTagJpaEntity::getTagId).toList();
        return tagRepository.findAllById(tagIds).stream().map(mapper::toDomain).toList();
    }

    @Override
    public Map<Long, List<Tag>> loadTagsGroupedByPostIds(List<Long> postIds) {
        if (postIds.isEmpty()) return Map.of();

        List<PostTagJpaEntity> postTags = postTagRepository.findByPostIdIn(postIds);
        List<Long> tagIds = postTags.stream().map(PostTagJpaEntity::getTagId).distinct().toList();
        Map<Long, Tag> tagMap = tagRepository.findAllById(tagIds).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toMap(Tag::id, t -> t));

        return postTags.stream().collect(Collectors.groupingBy(
                PostTagJpaEntity::getPostId,
                Collectors.mapping(pt -> tagMap.get(pt.getTagId()), Collectors.toList())
        ));
    }

    @Override
    public List<Tag> loadAllByUsageCount() {
        return tagRepository.findAllOrderByUsageCount().stream()
                .map(v -> new Tag(v.getId(), v.getName()))
                .toList();
    }

    @Override
    public void savePostTags(Long postId, List<Long> tagIds) {
        List<PostTagJpaEntity> entities = tagIds.stream()
                .map(tagId -> new PostTagJpaEntity(postId, tagId))
                .toList();
        postTagRepository.saveAll(entities);
    }

    @Override
    public void deletePostTagsByPostId(Long postId) {
        postTagRepository.deleteByPostId(postId);
    }
}
