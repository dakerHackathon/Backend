package com.daker.repository;

import com.daker.domain.entity.Hackathon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HackathonRepository extends JpaRepository<Hackathon, Long> {
}
