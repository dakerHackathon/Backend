package com.daker.repository;

import com.daker.domain.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

        @Query("""
        SELECT DISTINCT a
        FROM Article a
        JOIN FETCH a.team t
        LEFT JOIN FETCH a.targetPositions tp
        LEFT JOIN FETCH tp.position p
        WHERE a.isOpen = :isOpen
        ORDER BY a.createdAt DESC
    """)
        List<Article> findAllByIsOpenWithTeam(@Param("isOpen") Boolean isOpen);

        @Query("""
        SELECT DISTINCT a
        FROM Article a
        JOIN FETCH a.team t
        JOIN FETCH a.targetPositions tp
        JOIN FETCH tp.position p
        WHERE a.isOpen = :isOpen
          AND p.id = :positionId
        ORDER BY a.createdAt DESC
    """)
        List<Article> findAllByIsOpenAndPositionWithTeam(@Param("isOpen") Boolean isOpen, @Param("positionId") Long positionId);
}
