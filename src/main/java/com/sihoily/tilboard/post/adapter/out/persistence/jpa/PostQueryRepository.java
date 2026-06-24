package com.sihoily.tilboard.post.adapter.out.persistence.jpa;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<PostJpaEntity> findPosts(String keyword, Pageable pageable) {
        QPostJpaEntity post = QPostJpaEntity.postJpaEntity;

        List<PostJpaEntity> content = queryFactory
                .selectFrom(post)
                .where(post.deletedAt.isNull(), titleContains(post, keyword))
                .orderBy(toOrderSpecifiers(post, pageable.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(post.count())
                .from(post)
                .where(post.deletedAt.isNull(), titleContains(post, keyword))
                .fetchOne();

        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }

    private BooleanExpression titleContains(QPostJpaEntity post, String keyword) {
        return StringUtils.hasText(keyword) ? post.title.containsIgnoreCase(keyword) : null;
    }

    private OrderSpecifier<?>[] toOrderSpecifiers(QPostJpaEntity post, Sort sort) {
        return sort.stream()
                .map(order -> {
                    Order direction = order.isAscending() ? Order.ASC : Order.DESC;
                    return switch (order.getProperty()) {
                        case "updatedAt" -> new OrderSpecifier<>(direction, post.updatedAt);
                        default -> new OrderSpecifier<>(direction, post.createdAt);
                    };
                })
                .toArray(OrderSpecifier[]::new);
    }
}
