package com.numberone.backend.domain.disaster.service;

import com.numberone.backend.domain.conversation.dto.response.GetConversationResponse;
import com.numberone.backend.domain.conversation.entity.Conversation;
import com.numberone.backend.domain.conversation.repository.ConversationRepository;
import com.numberone.backend.domain.conversation.service.ConversationService;
import com.numberone.backend.domain.disaster.dto.request.LatestDisasterRequest;
import com.numberone.backend.domain.disaster.dto.request.SaveDisasterRequest;
import com.numberone.backend.domain.disaster.dto.response.LatestDisasterResponse;
import com.numberone.backend.domain.disaster.dto.response.SituationDetailResponse;
import com.numberone.backend.domain.disaster.dto.response.SituationHomeResponse;
import com.numberone.backend.domain.disaster.dto.response.SituationResponse;
import com.numberone.backend.domain.disaster.entity.Disaster;
import com.numberone.backend.domain.disaster.repository.DisasterRepository;
import com.numberone.backend.domain.disaster.util.DisasterType;
import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.member.service.MemberService;
import com.numberone.backend.domain.notificationdisaster.entity.NotificationDisaster;
import com.numberone.backend.domain.notificationregion.entity.NotificationRegion;
import com.numberone.backend.exception.notfound.NotFoundDisasterException;
import com.numberone.backend.util.LocationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DisasterService {
    private final DisasterRepository disasterRepository;
    private final LocationProvider locationProvider;
    private final MemberService memberService;
    private final ConversationService conversationService;
    private final ConversationRepository conversationRepository;

    public LatestDisasterResponse getLatestDisaster(LatestDisasterRequest latestDisasterRequest) {
        String address = locationProvider.pos2address(latestDisasterRequest.getLatitude(), latestDisasterRequest.getLongitude());
        LocalDateTime time = LocalDateTime.now().minusDays(1);
        List<Disaster> disasters = disasterRepository.findDisastersInAddressAfterTime(address, time);
        if (!disasters.isEmpty())
            return LatestDisasterResponse.of(disasters.get(0));
        else
            return LatestDisasterResponse.notExist();
    }

    @Transactional
    public void save(SaveDisasterRequest saveDisasterRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(saveDisasterRequest.getCreatedAt(), formatter);
        Disaster disaster = Disaster.of(
                saveDisasterRequest.getDisasterType(),
                saveDisasterRequest.getLocation(),
                saveDisasterRequest.getMsg(),
                saveDisasterRequest.getDisasterNum(),
                dateTime
        );
        disasterRepository.save(disaster);
    }

    private boolean isValidDisasterType(DisasterType disasterType, List<NotificationDisaster> notificationDisasters) {
        for (NotificationDisaster notificationDisaster : notificationDisasters) {
            if (disasterType.equals(notificationDisaster.getDisasterType()))
                return true;
        }
        return false;
    }

    public SituationHomeResponse getSituationHome(String email) {
        Set<Disaster> disasters = new HashSet<>();
        Member member = memberService.findByEmail(email);
        LocalDateTime time = LocalDateTime.now().minusDays(1);
        for (NotificationRegion notificationRegion : member.getNotificationRegions()) {
            disasters.addAll(disasterRepository.findDisastersInAddressAfterTime(notificationRegion.getLocation(), time));
        }
        disasters.removeIf(disaster -> !isValidDisasterType(disaster.getDisasterType(), member.getNotificationDisasters()));


        List<SituationResponse> situationResponses = new ArrayList<>();
        for (Disaster disaster : disasters) {
            Long conversationCnt=0L;
            List<GetConversationResponse> conversationResponses = new ArrayList<>();
            conversationCnt+=conversationRepository.countByDisaster(disaster);
            List<Conversation> conversations = conversationRepository.findAllByDisasterOrderByLikeCntDesc(disaster, PageRequest.of(0,3));
            for (Conversation conversation : conversations) {
                conversationResponses.add(conversationService.getExceptChild(email, conversation.getId()));
                conversationCnt+=conversationRepository.countByParent(conversation);
            }
            situationResponses.add(SituationResponse.of(disaster, conversationResponses, conversationCnt));
        }

        return SituationHomeResponse.of(situationResponses);
    }

    public SituationDetailResponse getSituationDetail(String email, Long disasterId) {
        Disaster disaster = disasterRepository.findById(disasterId)
                .orElseThrow(NotFoundDisasterException::new);
        List<GetConversationResponse> conversationResponses = new ArrayList<>();
        for (Conversation conversation : disaster.getConversations()) {
            conversationResponses.add(conversationService.get(email, conversation.getId()));
        }
        return SituationDetailResponse.of(conversationResponses);
    }
}
