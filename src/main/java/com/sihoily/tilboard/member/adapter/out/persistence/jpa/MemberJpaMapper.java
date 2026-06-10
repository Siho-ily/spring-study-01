package com.sihoily.tilboard.member.adapter.out.persistence.jpa;

import com.sihoily.tilboard.member.domain.Member;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MemberJpaMapper {
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target="version", ignore=true)
    MemberJpaEntity toEntity(Member member);

//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Member toDomain(MemberJpaEntity entity);
}
