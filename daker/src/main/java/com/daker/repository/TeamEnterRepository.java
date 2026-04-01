package com.daker.repository;

import com.daker.domain.entity.User;
import com.daker.domain.entity.mapping.TeamEnter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamEnterRepository extends JpaRepository<TeamEnter, Long> {
    List<TeamEnter> findAllBySender(User user);

    List<TeamEnter> findAllBySenderAndType(User user, int type);
}
