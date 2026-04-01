package com.daker.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "HackathonDetail")
@Builder
@Data
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
    private LocalDate endApplyDate;

    @Column
    private LocalDate endSubmissionDate;

    @Column
    private LocalDate endEvaluationDate;

    @Lob
    private String submissionGuide;

    @Column
    private int first;

    @Column
    private int second;

    @Column
    private int third;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hackathon_id", nullable = false, unique = true)
    private Hackathon hackathon;
}