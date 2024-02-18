package com.numberone.backend.domain.conversation.controller;

import com.numberone.backend.domain.conversation.dto.request.CreateChildConversationRequest;
import com.numberone.backend.domain.conversation.dto.request.CreateConversationRequest;
import com.numberone.backend.domain.conversation.service.ConversationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "conversations", description = "대화(재난상황 댓글) 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/conversations")
public class ConversationController {
    private final ConversationService conversationService;

    @Operation(summary = "(대댓글x) 대화 생성하기", description = """
            대화 내용을 body에 담아 전달해주세요.
            
            어떤 재난상황에 대한 대화인지 재난상황 id를 body에 같이 담아 전달해주세요.
            """)
    @PostMapping()
    public void createConversation(Authentication authentication, @RequestBody @Valid CreateConversationRequest createConversationRequest) {
        conversationService.createConversation(authentication.getName(), createConversationRequest);
    }

    @Operation(summary = "대댓글 대화 생성하기", description = """
            대댓글 대화 내용을 body에 담아 전달해주세요.
            
            어떤 대화(댓글)의 대댓글인지 대화 id를 파라미터로 전달해주세요.
            """)
    @PostMapping("/{conversationId}")
    public void createChildConversation(Authentication authentication, @RequestBody @Valid CreateChildConversationRequest createConversationRequest, @PathVariable Long conversationId){
        conversationService.createChildConversation(authentication.getName(), createConversationRequest, conversationId);
    }

    @Operation(summary = "(대댓글도 포함)대화 삭제하기", description = """
            삭제할 대화 id를 파라미터로 전달해주세요.
            """)
    @DeleteMapping("/{conversationId}")
    public void delete(@PathVariable Long conversationId) {
        conversationService.delete(conversationId);
    }

//    만들었는데 필요없는 api인것 같아서 일단 주석처리
//    @Operation(summary = "대화 조회하기", description = """
//            조회할 대화 id를 파라미터로 전달해주세요.
//            """)
//    @GetMapping("/{conversationId}")
//    public ResponseEntity<GetConversationResponse> get(Authentication authentication, @PathVariable Long conversationId) {
//        return ResponseEntity.ok(conversationService.get(authentication.getName(), conversationId));
//    }

    @Operation(summary = "대화 좋아요 등록하기", description = """
            사용자가 대화의 좋아요를 등록할 때 대화 id를 파라미터로 전달해주세요.
            """)
    @PostMapping("/like/{conversationId}")
    public void increaseLike(Authentication authentication, @PathVariable Long conversationId) {
        conversationService.increaseLike(authentication.getName(), conversationId);
    }

    @Operation(summary = "대화 좋아요 취소하기", description = """
            사용자가 대화의 좋아요를 취소할 때 대화 id를 파라미터로 전달해주세요.
            """)
    @DeleteMapping("/like/{conversationId}")
    public void decreaseLike(Authentication authentication, @PathVariable Long conversationId) {
        conversationService.decreaseLike(authentication.getName(), conversationId);
    }
}
