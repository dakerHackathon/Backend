package com.daker.repository;

import com.daker.domain.entity.Article;
import com.daker.domain.entity.mapping.TargetPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TargetPositionRepository extends JpaRepository<TargetPosition, Long> {
    List<TargetPosition> findAllByArticle(Article article);
}
