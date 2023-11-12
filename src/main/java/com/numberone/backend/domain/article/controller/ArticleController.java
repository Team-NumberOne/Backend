package com.numberone.backend.domain.article.controller;

import com.numberone.backend.domain.article.dto.request.UploadArticleRequest;
import com.numberone.backend.domain.article.dto.response.*;
import com.numberone.backend.domain.article.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RequestMapping("/api/articles")
@RequiredArgsConstructor
@RestController
public class ArticleController {

    private final ArticleService articleService;


    @Operation(summary = "게시글 작성 API", description = """
                        
            동네생활 게시글 등록 api 입니다.
            반드시 access token 을 헤더에 담아서 요청해주세요.
                        
            1. title 은 글 제목 입니다 (not null)
            2. content 는 글 내용 입니다 (not null)
            3. articleTag 는 게시글 태그 입니다. LIFE(일상), FRAUD(사기), SAFETY(안전), REPORT(제보)
            4. imageList 는 이미지 (MultiPart) 리스트 입니다.
            5. thumbNailImageIdx 는 썸네일 이미지의 인덱스 입니다. (0,1,2, ...
            imageList 에 이미지를 담아서 보내는 경우,
            idx 에 따라서 썸네일 이미지를 결정합니다.
                        
            """)

    @PostMapping
    public ResponseEntity<UploadArticleResponse> uploadArticle(@RequestBody @Valid UploadArticleRequest request) {
        return ResponseEntity.created(URI.create("/api/articles"))
                .body(articleService.uploadArticle(request));
    }

    @Operation(summary = "게시글을 삭제하는 API 입니다.", description = """
            게시글 id 를 PathVariable 으로 넘겨주세요.
            해당 게시글을 삭제 상태로 변경합니다.
            """)
    @PutMapping("{article-id}/delete")
    public ResponseEntity<DeleteArticleResponse> deleteArticle(@PathVariable("article-id") Long articleId) {
        return ResponseEntity.ok(articleService.deleteArticle(articleId));
    }

    @Operation(summary = "게시글 상세 조회 API 입니다.", description = """
            게시글 id 를 PathVariable 으로 넘겨주세요.
            """)
    @GetMapping("{article-id}")
    public ResponseEntity<GetArticleDetailResponse> getArticleDetails(@PathVariable("article-id") Long articleId) {
        return ResponseEntity.ok(articleService.getArticleDetail(articleId));
    }


    @Operation(summary = "게시글 리스트 조회 no offset Paging API 입니다.", description = """
            
           요청 예시 url 은 다음과 같습니다.
           `/api/articles?size=5`
           size 는 페이지의 사이즈를 의미하고, default 는 20 입니다.
           
           정렬 순서는 articleId 순입니다. ( = 생성 시간 순 )
           
           ModelAttribute 로 lastArticleId 와 tag 를 넘겨주세요 ( 둘 다 nullable )
           
           tag 가 null 이면, tag 상관 없이 전체 조회를 수행합니다.
           tag 가 null 이 아니면, 해당 tag 에 해당하는 게시글만 조회합니다.
           
           lastArticleId 는 직전에 조회한 게시글 중 가장 먀지막(작은) articleId 를 의미합니다.
           - 첫 페이지를 요청할 경우에는 lastArticleId 를 null 로 보내야합니다.
           - 첫 페이지 이후에 대한 요청은, 직전 페이지 요청에서 얻어온 lastArticleId 를 넣어서 보내면 그 다음 페이지를 호출합니다.
           
            """)
    @GetMapping
    public ResponseEntity<Slice<GetArticleListResponse>> getArticlePages(
            Pageable pageable,
            @ModelAttribute ArticleSearchParameter param){
        return ResponseEntity.ok(articleService.getArticleListPaging(param, pageable));
    }

}
