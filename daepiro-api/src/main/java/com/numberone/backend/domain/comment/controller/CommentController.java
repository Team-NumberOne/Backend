package com.numberone.backend.domain.comment.controller;

import com.numberone.backend.domain.comment.dto.request.CreateChildCommentRequest;
import com.numberone.backend.domain.comment.dto.response.CreateChildCommentResponse;
import com.numberone.backend.domain.comment.dto.response.DeleteCommentResponse;
import com.numberone.backend.domain.comment.dto.GetCommentDto;
import com.numberone.backend.domain.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RequestMapping("api/comments")
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
            @RequestBody @Valid CreateChildCommentRequest request) {
        CreateChildCommentResponse response = commentService.createChildComment(articleId, commentId, request);
        return ResponseEntity.created(
                        URI.create(String.format("/comments/%s/%s", articleId, commentId)))
                .body(response);
    }

    @Operation(summary = "해당 게시물에 달린 댓긂을 모두 조회하는 API 입니다.", description = """
            해당 게시물에 달린 댓글을 계층 형태로 조회합니다.
            
                - Long commentId : 댓글 아이디
                - Long parentCommentId : 부모 댓글의 야이디 (nullable)
                - List<GetCommentDto> childComments = new ArrayList<>() : 대댓글 리스트 
                - Integer likeCount : 해당 댓글의 좋아요 개수
                - LocalDateTime createdAt : 해당 댓글의 생성 시각
                - LocalDateTime modifiedAt : 해당 댓글의 마지막 수정 시각
                - String content : 해당 댓글의 내용
                - Long authorId : 해당 댓글의 작성자 아이디
                - String authorNickName : 해당 댓글의 작성자 닉네임
                - String authorProfileImageUrl : 해당 댓글 작성자의 프로필 사진 url
                
             댓글 작성자가 추후에 탈퇴하는 경우를 고려했는데,
             authorNickName 이 "알수없는 사용자" 로 변경되어 내려갑니다..!
            """)
    @GetMapping("{article-id}")
    public ResponseEntity<List<GetCommentDto>> getCommentsByArticle(@PathVariable("article-id") Long articleId){
        List<GetCommentDto> response = commentService.getCommentsByArticle(articleId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "댓글 삭제 API 입니다", description = """
            삭제할 댓글의 id 를 path variable 으로 보내주세요.
            
            대댓글이 존재하는 댓글을 삭제 요청하는 경우에는, 대댓글까지 모두 삭제됩니다.
            
            대댓글이 없는 댓글을 삭제 요청하는 경우에는 해당 댓글만 삭제됩니다.
            """)
    @DeleteMapping("{comment-id}")
    public ResponseEntity<DeleteCommentResponse> deleteComment(@PathVariable("comment-id") Long commentId){
        return ResponseEntity.ok(commentService.deleteComment(commentId));
    }

    // todo: 가장 많은 좋아요 상단 고정, 대댓글 달리면 푸시 알람 전송, 상단 고정된 작성자에게 푸시알람 전송, 댓글 신고 기능

}
