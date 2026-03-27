package com.daker.repository;

import com.daker.domain.entity.Bookmark;
import com.daker.domain.entity.Hackathon;
import com.daker.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    @Query("SELECT b FROM Bookmark b WHERE b.user = :user AND b.hackathon = :hackathon")
    Optional<Bookmark> findByUserAndHackathon(@Param("user") User user, @Param("hackathon") Hackathon hackathon);
}