package com.sihoily.tilboard.tag.adapter.out.persistence.jpa;

import com.sihoily.tilboard.tag.domain.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagJpaMapper {
    TagJpaEntity toEntity(Tag tag);
    Tag toDomain(TagJpaEntity entity);
}
