package com.numberone.backend.domain.articleimage.repository;

import com.numberone.backend.domain.article.entity.Article;
import com.numberone.backend.domain.articleimage.entity.ArticleImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleImageRepository extends JpaRepository<ArticleImage, Long> {

    List<ArticleImage> findByArticle(Article article);
}
