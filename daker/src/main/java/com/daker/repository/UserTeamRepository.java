package com.daker.repository;

import com.daker.domain.entity.Team;
import com.daker.domain.entity.User;
import com.daker.domain.entity.mapping.UserTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserTeamRepository extends JpaRepository<UserTeam, Long> {
    @Query("SELECT ut FROM UserTeam ut WHERE ut.team = :team AND ut.user = :user")
    UserTeam findByUserAndTeam(@Param("user") User user, @Param("team") Team team);

    @Query("SELECT ut FROM UserTeam ut WHERE ut.user = :user")
    List<UserTeam> findAllByUser(@Param("user") User user);

    @Query("SELECT ut.user FROM UserTeam ut WHERE ut.team = :team")
    List<User> findAllUsersByTeam(@Param("team") Team team);

    @Query("SELECT ut.team FROM UserTeam ut WHERE ut.user = :user AND ut.leader = true")
    List<Team> findOwnTeamsByUser(@Param("user") User user);
}
