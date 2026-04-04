package com.daker.repository;

import com.daker.domain.entity.Hackathon;
import com.daker.domain.entity.Team;
import com.daker.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query("SELECT t FROM Team t Join TeamHackathon th JOIN UserTeam ut WHERE th.hackathon = :hackathon AND ut.user = :user")
    Team findMyTeamInHackathon(@Param("hackathon") Hackathon hackathon, @Param("user") User user);
}
