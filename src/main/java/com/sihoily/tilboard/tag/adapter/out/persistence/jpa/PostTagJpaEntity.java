package com.sihoily.tilboard.tag.adapter.out.persistence.jpa;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "post_tags")
@IdClass(PostTagId.class)
public class PostTagJpaEntity {

    @Id
    private Long postId;

    @Id
    private Long tagId;
}
