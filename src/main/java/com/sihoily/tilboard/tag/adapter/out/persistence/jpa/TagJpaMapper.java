package com.sihoily.tilboard.tag.adapter.out.persistence.jpa;

import com.sihoily.tilboard.tag.domain.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TagJpaMapper {

    @Mapping(target = "createdAt", ignore = true)
    TagJpaEntity toEntity(Tag tag);

    Tag toDomain(TagJpaEntity entity);
}
