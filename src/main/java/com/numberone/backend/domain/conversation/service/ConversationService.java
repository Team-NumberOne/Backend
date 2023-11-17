package com.numberone.backend.domain.conversation.service;

import com.numberone.backend.domain.conversation.dto.request.CreateChildConversationRequest;
import com.numberone.backend.domain.conversation.dto.request.CreateConversationRequest;
import com.numberone.backend.domain.conversation.dto.response.GetConversationResponse;
import com.numberone.backend.domain.conversation.entity.Conversation;
import com.numberone.backend.domain.conversation.repository.ConversationRepository;
import com.numberone.backend.domain.disaster.entity.Disaster;
import com.numberone.backend.domain.disaster.repository.DisasterRepository;
import com.numberone.backend.domain.like.entity.ConversationLike;
import com.numberone.backend.domain.like.repository.ConversationLikeRepository;
import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.member.service.MemberService;
import com.numberone.backend.exception.conflict.AlreadyLikedException;
import com.numberone.backend.exception.conflict.AlreadyUnLikedException;
import com.numberone.backend.exception.notfound.NotFoundConversationException;
import com.numberone.backend.exception.notfound.NotFoundDisasterException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConversationService {
    private final ConversationRepository conversationRepository;
    private final MemberService memberService;
    private final DisasterRepository disasterRepository;
    private final ConversationLikeRepository conversationLikeRepository;

    @Transactional
    public void createConversation(String email, CreateConversationRequest createConversationRequest) {
        Member member = memberService.findByEmail(email);
        Disaster disaster = disasterRepository.findById(createConversationRequest.getDisasterId())
                .orElseThrow(NotFoundDisasterException::new);
        Conversation conversation = Conversation.of(
                createConversationRequest.getContent(),
                member,
                disaster
        );
        conversationRepository.save(conversation);
    }

    @Transactional
    public void createChildConversation(String email, CreateChildConversationRequest createConversationRequest, Long conversationId) {
        Member member = memberService.findByEmail(email);
        Conversation parent = conversationRepository.findById(conversationId)
                .orElseThrow(NotFoundConversationException::new);
        Conversation conversation = Conversation.childOf(
                createConversationRequest.getContent(),
                member,
                parent
        );
        conversationRepository.save(conversation);
    }

    @Transactional
    public void delete(Long conversationId) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(NotFoundConversationException::new);
        conversationRepository.delete(conversation);
    }

    private boolean checkLike(Member member, Conversation conversation) {
        for (ConversationLike conversationLike : conversation.getLikes()) {
            if (conversationLike.getMember().equals(member)) {
                return true;
            }
        }
        return false;
    }

    public GetConversationResponse get(String email, Long conversationId) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(NotFoundConversationException::new);
        Member member = memberService.findByEmail(email);
        List<GetConversationResponse> childs = new ArrayList<>();
        for (Conversation child : conversation.getConversations()) {
            childs.add(GetConversationResponse.of(
                    child,
                    checkLike(member, child),
                    member.equals(child.getMember()),
                    new ArrayList<>()
            ));
        }
        return GetConversationResponse.of(
                conversation,
                checkLike(member, conversation),
                member.equals(conversation.getMember()),
                childs);
    }

    public GetConversationResponse getExceptChild(String email, Long conversationId) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(NotFoundConversationException::new);
        Member member = memberService.findByEmail(email);
        return GetConversationResponse.of(
                conversation,
                checkLike(member, conversation),
                member.equals(conversation.getMember()),
                new ArrayList<>());
    }

    @Transactional
    public void increaseLike(String email, Long conversationId) {
        Member member = memberService.findByEmail(email);
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(NotFoundConversationException::new);
        conversationLikeRepository.findByMemberAndConversation(member, conversation)
                .ifPresent((o) -> {
                    throw new AlreadyLikedException();
                });
        ConversationLike conversationLike = ConversationLike.of(member, conversation);
        conversationLikeRepository.save(conversationLike);
        conversation.increaseLike();
    }

    @Transactional
    public void decreaseLike(String email, Long conversationId) {
        Member member = memberService.findByEmail(email);
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(NotFoundConversationException::new);
        ConversationLike conversationLike = conversationLikeRepository.findByMemberAndConversation(member, conversation)
                .orElseThrow(AlreadyUnLikedException::new);
        conversationLikeRepository.delete(conversationLike);
        conversation.decreaseLike();
    }
}
