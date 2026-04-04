package com.daker.domain.entity;

import com.daker.domain.entity.mapping.TargetPosition;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Article")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(length = 255)
    private String title;

    @Column
    private String content;

    @Column
    private Boolean isOpen;

    @Column(length = 255)
    private String contact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User writer;

    @Column
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TargetPosition> targetPositions = new ArrayList<>();
}