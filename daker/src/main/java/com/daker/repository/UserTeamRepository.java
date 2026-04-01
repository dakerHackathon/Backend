package com.daker.repository;

import com.daker.domain.entity.Position;
import com.daker.domain.entity.Team;
import com.daker.domain.entity.User;
import com.daker.domain.entity.mapping.UserTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserTeamRepository extends JpaRepository<UserTeam, Long> {
    @Query("SELECT ut FROM UserTeam ut WHERE ut.team = :team AND ut.user = :user")
    Optional<UserTeam> findByUserAndTeam(@Param("user") User user, @Param("team") Team team);

    @Query("SELECT ut FROM UserTeam ut WHERE ut.user = :user")
    List<UserTeam> findAllByUser(@Param("user") User user);

    @Query("SELECT ut.user FROM UserTeam ut WHERE ut.team = :team")
    List<User> findAllUsersByTeam(@Param("team") Team team);

    @Query("SELECT ut.team FROM UserTeam ut WHERE ut.user = :user AND ut.leader = true")
    List<Team> findOwnTeamsByUser(@Param("user") User user);

    @Query("SELECT ut.user FROM UserTeam ut JOIN TeamHackathon th ON ut.team = th.team GROUP BY ut.user ORDER BY count(th.hackathon.id) DESC LIMIT 10")
    List<User> getPartRankings();

    @Query("SELECT ut.user FROM UserTeam ut JOIN TeamHackathon th ON ut.team = th.team WHERE th.ranking = 1 GROUP BY ut.user ORDER BY count(th.hackathon.id) DESC LIMIT 10")
    List<User> getWinRankings();

    @Query("""
        SELECT COUNT(u) + 1
        FROM User u
        WHERE (
            SELECT COUNT(DISTINCT th.hackathon.id)
            FROM UserTeam ut
            JOIN TeamHackathon th ON th.team = ut.team
            WHERE ut.user = u
              AND th.ranking = 1
        ) > (
            SELECT COUNT(DISTINCT th2.hackathon.id)
            FROM UserTeam ut2
            JOIN TeamHackathon th2 ON th2.team = ut2.team
            WHERE ut2.user = :user
              AND th2.ranking = 1
        )
    """)
    int getMyWinRank(User user);

    @Query("SELECT COUNT(DISTINCT th.hackathon.id) FROM UserTeam ut JOIN TeamHackathon th ON th.team = ut.team WHERE ut.user = :user AND th.ranking = 1")
    int getWinCount(@Param("user") User user);

    @Query("""
        SELECT COUNT(u) + 1
        FROM User u
        WHERE (
            SELECT COUNT(DISTINCT th.hackathon.id)
            FROM UserTeam ut
            JOIN TeamHackathon th ON th.team = ut.team
            WHERE ut.user = u
        ) > (
            SELECT COUNT(DISTINCT th2.hackathon.id)
            FROM UserTeam ut2
            JOIN TeamHackathon th2 ON th2.team = ut2.team
            WHERE ut2.user = :user
        )
    """)
    int getMyPartRank(@Param("user") User user);

    @Query("SELECT COUNT(DISTINCT th.hackathon.id) FROM UserTeam ut JOIN TeamHackathon th ON th.team = ut.team WHERE ut.user = :user")
    int getPartCount(User user);

    @Query("SELECT ut.position FROM UserTeam ut WHERE ut.user = :user AND ut.team = :team")
    Position getPositionByUserANDTeam(User user, Team team);

    @Query("SELECT ut.user FROM UserTeam ut WHERE ut.team = :team AND ut.leader = true")
    User findLeaderByTeam(@Param("team") Team team);
}
