package com.sihoily.tilboard.post.adapter.out.persistence.jpa;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<PostJpaEntity> findPosts(String keyword) {
        QPostJpaEntity post = QPostJpaEntity.postJpaEntity;
        return queryFactory
                .selectFrom(post)
                .where(
                        post.deletedAt.isNull(),
                        titleContains(post, keyword)
                )
                .orderBy(post.createdAt.desc())
                .fetch();
    }

    private BooleanExpression titleContains(QPostJpaEntity post, String keyword) {
        return StringUtils.hasText(keyword) ? post.title.containsIgnoreCase(keyword) : null;
    }
}
