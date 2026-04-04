package com.daker.domain.entity;

import com.daker.domain.entity.mapping.Bookmark;
import com.daker.domain.entity.mapping.TeamHackathon;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Hackathon")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hackathon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    private String title;

    @Column
    private LocalDateTime startAt;

    @Column
    private LocalDateTime endAt;

    @Column(length = 255)
    private String location;

    @Column
    private String cond;

    @OneToMany(mappedBy = "hackathon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "hackathon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeamHackathon> teamHackathons = new ArrayList<>();

    @OneToOne(mappedBy = "hackathon", cascade = CascadeType.ALL, orphanRemoval = true)
    private HackathonDetail hackathonDetail;

    @OneToMany(mappedBy = "hackathon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HackathonEvaluationCriteria> hackathonEvaluationCriteria;

    @OneToMany(mappedBy = "hackathon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HackathonSchedule> hackathonSchedules;
}