package com.sihoily.tilboard.tag.adapter.out.persistence.jpa;

import com.sihoily.tilboard.tag.application.port.out.DeleteTagPort;
import com.sihoily.tilboard.tag.application.port.out.LoadTagPort;
import com.sihoily.tilboard.tag.application.port.out.SaveTagPort;
import com.sihoily.tilboard.tag.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TagJpaPersistenceAdapter implements SaveTagPort, LoadTagPort, DeleteTagPort {

    private final TagJpaRepository repository;
    private final TagJpaMapper mapper;

    @Override
    public Tag saveTag(Tag tag) {
        return mapper.toDomain(repository.save(mapper.toEntity(tag)));
    }

    @Override
    public Optional<Tag> loadTag(Long tagId) {
        return repository.findById(tagId).map(mapper::toDomain);
    }

    @Override
    public List<Tag> loadTagsByPostId(Long postId) {
        return repository.findByPostId(postId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteTag(Long tagId) {
        repository.deleteById(tagId);
    }
}
