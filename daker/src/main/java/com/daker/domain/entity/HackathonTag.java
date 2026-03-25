package com.daker.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "HackathonTag")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HackathonTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hackathon_id")
    private Hackathon hackathon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;
}