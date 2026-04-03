package com.daker.repository;

import com.daker.domain.entity.Team;
import com.daker.domain.entity.TemperatureSet;
import com.daker.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TemperatureSetRepository extends JpaRepository<TemperatureSet, Long> {
    @Query("SELECT ts FROM TemperatureSet ts WHERE ts.team = :team AND ts.fromUser = :fromUser AND ts.toUser = :toUser")
    Optional<TemperatureSet> findByFromUserAndToUserAndTeam(@Param("fromUser") User fromUser, @Param("toUser") User toUser, @Param("team") Team team);
}