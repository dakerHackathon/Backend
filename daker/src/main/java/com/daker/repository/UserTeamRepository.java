package com.daker.repository;

import com.daker.domain.entity.Team;
import com.daker.domain.entity.User;
import com.daker.domain.entity.UserTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserTeamRepository extends JpaRepository<UserTeam, Long> {
}
