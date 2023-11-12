package com.numberone.backend.domain.article.controller;

import com.numberone.backend.domain.article.dto.request.UploadArticleRequest;
import com.numberone.backend.domain.article.dto.response.UploadArticleResponse;
import com.numberone.backend.domain.article.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@RequestMapping("/api/articles")
@RequiredArgsConstructor
@RestController
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<UploadArticleResponse> uploadArticle(@RequestBody @Valid UploadArticleRequest request){
        return ResponseEntity.created(URI.create("/api/articles"))
                .body(articleService.uploadArticle(request));
    }

}
