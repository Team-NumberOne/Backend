package com.numberone.backend.domain.comment.controller;

import com.numberone.backend.domain.comment.dto.request.CreateChildCommentRequest;
import com.numberone.backend.domain.comment.dto.response.CreateChildCommentResponse;
import com.numberone.backend.domain.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RequestMapping("/comments")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "대댓글 작성 API", description = """
            comment-id 는 부모 댓글의 id 입니다.
            article-id 와 comment-id 는 모두 path variable 으로 보내주세요!
            """)
    @PostMapping("{article-id}/{comment-id}")
    public ResponseEntity<CreateChildCommentResponse> createChildComment(
            @PathVariable("article-id") Long articleId,
            @PathVariable("comment-id") Long commentId,
            @RequestBody @Valid CreateChildCommentRequest request ){
        CreateChildCommentResponse response = commentService.createChildComment(articleId, commentId, request);
        return ResponseEntity.created(
                URI.create(String.format("/comments/%s/%s", articleId, commentId)))
                .body(response);
    }

}
