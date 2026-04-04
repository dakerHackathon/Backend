package com.daker.repository;

import com.daker.domain.entity.Hackathon;
import com.daker.domain.entity.Team;
import com.daker.domain.entity.User;
import com.daker.domain.entity.mapping.TeamHackathon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface TeamHackathonRepository extends JpaRepository<TeamHackathon, Long> {

    @Query("SELECT th FROM TeamHackathon th WHERE th.team = :team")
    List<TeamHackathon> findAllByTeam(@Param("team") Team team);

    @Query("""
        SELECT th
        FROM TeamHackathon th
        JOIN FETCH th.hackathon
        WHERE th.team = :team
    """)
    Optional<TeamHackathon> findFirstByTeam(@Param("team") Team team);

    List<TeamHackathon> findAllByHackathon(Hackathon hackathon);

    @Query("SELECT th FROM TeamHackathon th WHERE th.team = :team")
    Optional<Object> findByTeamAndHackathon(@Param("team")Team team);

    @Query("SELECT th FROM TeamHackathon th JOIN UserTeam ut ON th.team = ut.team WHERE ut.user = :user AND th.hackathon = :hackathon")
    TeamHackathon findByUserAndHackathon(@Param("user") User user, @Param("hackathon") Hackathon hackathon);
}
