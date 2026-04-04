package com.daker.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "HackathonDetail")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HackathonDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String subTitle;

    @Column
    private String description;

    @Column
    private String organizer;

    @Column
    private LocalDateTime endApplyDate;

    @Column
    private LocalDateTime endSubmissionDate;

    @Column
    private LocalDateTime endEvaluationDate;

    @Lob
    private String submissionGuide;

    @Column
    private int first;

    @Column
    private int second;

    @Column
    private int third;

    @Column
    private String submitURL;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hackathon_id", nullable = false, unique = true)
    private Hackathon hackathon;
}