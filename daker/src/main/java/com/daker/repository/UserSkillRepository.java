package com.daker.repository;

import com.daker.domain.entity.Skill;
import com.daker.domain.entity.User;
import com.daker.domain.entity.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {
    @Query("SELECT us FROM UserSkill us WHERE us.user = :user")
    List<UserSkill> findSkillsByUser(@Param("user") User user);
}
