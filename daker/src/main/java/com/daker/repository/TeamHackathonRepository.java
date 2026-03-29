package com.daker.repository;

import com.daker.domain.entity.Hackathon;
import com.daker.domain.entity.Team;
import com.daker.domain.entity.TeamHackathon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamHackathonRepository extends JpaRepository<TeamHackathon, Long> {

    @Query("SELECT th FROM TeamHackathon th WHERE th.team = :team")
    List<TeamHackathon> findAllByTeam(@Param("team") Team team);
}
