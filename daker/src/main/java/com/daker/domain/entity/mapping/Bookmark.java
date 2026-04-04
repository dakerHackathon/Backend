package com.daker.domain.entity.mapping;

import com.daker.domain.entity.Hackathon;
import com.daker.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Bookmark")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hackathon_id")
    private Hackathon hackathon;
}