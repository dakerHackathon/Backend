package com.daker.repository;

import com.daker.domain.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a WHERE a.isOpen = :isOpen")
    List<Article> findAllByIsOpenWithTeam(@Param("isOpen") Boolean isOpen);

    @Query("""
SELECT DISTINCT a
FROM Article a
JOIN FETCH a.team t
LEFT JOIN FETCH a.targetPositions tp
LEFT JOIN FETCH tp.position p
ORDER BY a.createdAt DESC
""")
    List<Article> findAllWithTeam();

    @Query("""
SELECT DISTINCT a
FROM Article a
JOIN FETCH a.team t
LEFT JOIN FETCH a.targetPositions tp
LEFT JOIN FETCH tp.position p
WHERE a.title LIKE %:keyword%
OR a.content LIKE %:keyword%
ORDER BY a.createdAt DESC
""")
    List<Article> searchByTitleOrContent(@Param("keyword") String keyword);

    @Query("""
SELECT DISTINCT a
FROM Article a
JOIN FETCH a.team t
LEFT JOIN FETCH a.targetPositions tp
LEFT JOIN FETCH tp.position p
JOIN TeamHackathon th ON th.team = t
JOIN th.hackathon h
WHERE h.title LIKE %:keyword%
ORDER BY a.createdAt DESC
""")
    List<Article> searchByHackathonTitle(@Param("keyword") String keyword);
}
