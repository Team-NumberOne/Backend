package com.numberone.backend.domain.article.repository;

import com.numberone.backend.domain.article.entity.Article;
import com.numberone.backend.domain.article.repository.custom.ArticleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryCustom {

    @Query("SELECT a FROM Article a LEFT JOIN FETCH a.articleOwner LEFT JOIN FETCH a.articleImages WHERE a.id = :articleId")
    Optional<Article> findByIdFetchJoin(@Param("articleId") Long articleId);

}
