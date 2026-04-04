package com.daker.domain.entity.mapping;

import com.daker.domain.entity.Skill;
import com.daker.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "UserSkill")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id")
    private Skill skill;
}