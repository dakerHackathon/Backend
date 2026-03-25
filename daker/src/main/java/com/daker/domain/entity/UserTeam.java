package com.daker.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "UserTeam")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    @Column
    private Boolean leader;
}