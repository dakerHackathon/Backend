package com.daker.domain.entity.mapping;

import com.daker.domain.entity.Article;
import com.daker.domain.entity.Position;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TargetPosition")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TargetPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    @Column
    private Integer count;
}