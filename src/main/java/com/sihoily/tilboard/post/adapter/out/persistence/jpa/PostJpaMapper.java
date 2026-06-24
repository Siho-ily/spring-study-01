package com.sihoily.tilboard.post.adapter.out.persistence.jpa;

import com.sihoily.tilboard.post.domain.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostJpaMapper {

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    PostJpaEntity toEntity(Post post);

    Post toDomain(PostJpaEntity entity);
}
