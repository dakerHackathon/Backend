package com.daker.domain.entity.mapping;

import com.daker.domain.entity.Hackathon;
import com.daker.domain.entity.Team;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TeamHackathon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamHackathon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hackathon_id")
    private Hackathon hackathon;

    @Column
    private Integer ranking;
}