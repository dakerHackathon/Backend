package com.daker.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TeamEnter")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamEnter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Column
    private Boolean fromTeam;
}