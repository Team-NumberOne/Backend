package com.numberone.backend.domain.articleimage.repository;

import com.numberone.backend.domain.articleimage.entity.ArticleImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleImageRepository extends JpaRepository<ArticleImage, Long> {
}
