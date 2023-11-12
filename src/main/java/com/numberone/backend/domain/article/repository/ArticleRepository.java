package com.numberone.backend.domain.article.repository;

import com.numberone.backend.domain.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
