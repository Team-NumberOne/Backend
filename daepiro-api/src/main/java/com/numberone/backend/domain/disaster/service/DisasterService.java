package com.numberone.backend.domain.disaster.service;

import com.numberone.backend.domain.admin.dto.SaveAdminDisasterRequest;
import com.numberone.backend.domain.conversation.dto.response.GetConversationResponse;
import com.numberone.backend.domain.conversation.entity.Conversation;
import com.numberone.backend.domain.conversation.repository.ConversationRepository;
import com.numberone.backend.domain.conversation.service.ConversationService;
import com.numberone.backend.domain.disaster.dto.request.LatestDisasterRequest;
import com.numberone.backend.domain.disaster.dto.response.LatestDisasterResponse;
import com.numberone.backend.domain.disaster.dto.response.SituationDetailResponse;
import com.numberone.backend.domain.disaster.dto.response.SituationHomeResponse;
import com.numberone.backend.domain.disaster.dto.response.SituationResponse;
import com.numberone.backend.domain.disaster.entity.Disaster;
import com.numberone.backend.domain.disaster.event.DisasterEvent;
import com.numberone.backend.domain.disaster.repository.DisasterRepository;
import com.numberone.backend.domain.disaster.DisasterType;
import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.member.repository.MemberRepository;
import com.numberone.backend.domain.member.service.MemberService;
import com.numberone.backend.domain.notificationdisaster.entity.NotificationDisaster;
import com.numberone.backend.domain.notificationregion.entity.NotificationRegion;
import com.numberone.backend.exception.badrequest.BadRequestConversationSortException;
import com.numberone.backend.exception.notfound.NotFoundDisasterException;
import com.numberone.backend.exception.notfound.NotFoundMemberException;
import com.numberone.backend.provider.location.LocationProvider;
import com.numberone.backend.provider.security.SecurityContextProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DisasterService {
    private final DisasterRepository disasterRepository;
    private final LocationProvider locationProvider;
    private final MemberRepository memberRepository;
    private final ConversationService conversationService;
    private final ConversationRepository conversationRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public LatestDisasterResponse getLatestDisaster(LatestDisasterRequest latestDisasterRequest) {
        String address = locationProvider.pos2address(latestDisasterRequest.getLatitude(), latestDisasterRequest.getLongitude());
        LocalDateTime time = LocalDateTime.now().minusDays(1);
        Set<Disaster> disasters = new HashSet<>(disasterRepository.findDisastersInAddressAfterTime(address, time));
        long id = SecurityContextProvider.getAuthenticatedUserId();
        Member member = memberRepository.findById(id)
                .orElseThrow(NotFoundMemberException::new);
        member.updateGps(latestDisasterRequest.getLatitude(), latestDisasterRequest.getLongitude(), address);
        String[] locationTokens = member.getLocation().split(" ");
        switch (locationTokens.length) {
            case 1 -> {
                // 서울특별시
                member.updateLv1(locationTokens[0]);
            }
            case 2 -> {
                // 서울특별시 광진구
                member.updateLv1(locationTokens[0]);
                member.updateLv2(locationTokens[1]);
            }
            case 3 -> {
                // 서울특별시 광진구 자양동
                member.updateLv1(locationTokens[0]);
                member.updateLv2(locationTokens[1]);
                member.updateLv3(locationTokens[2]);
            }
        }
        for (NotificationRegion notificationRegion : member.getNotificationRegions()) {
            disasters.addAll(disasterRepository.findDisastersInAddressAfterTime(notificationRegion.getLocation(), time));
        }
        disasters.removeIf(disaster -> !isValidDisasterType(disaster.getDisasterType(), member.getNotificationDisasters()));

        if (disasters.isEmpty())
            return LatestDisasterResponse.notExist();

        return LatestDisasterResponse.of(disasters.stream()
                .sorted(Comparator.comparing(Disaster::getGeneratedAt).reversed())
                .toList()
                .get(0));
    }

    @Transactional
    public void save(SaveAdminDisasterRequest saveDisasterRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(saveDisasterRequest.getCreatedAt(), formatter);
        Disaster savedDisaster =
                disasterRepository.save(
                        Disaster.of(
                                saveDisasterRequest.getDisasterType(),
                                saveDisasterRequest.getLocation(),
                                saveDisasterRequest.getMsg(),
                                saveDisasterRequest.getDisasterNum(),
                                dateTime
                        )
                );
        log.info("재난 발생 이벤트 발행");
        eventPublisher.publishEvent(DisasterEvent.of(savedDisaster)); // 신규 재난 발생 이벤트
    }

    private boolean isValidDisasterType(DisasterType disasterType, List<NotificationDisaster> notificationDisasters) {
        for (NotificationDisaster notificationDisaster : notificationDisasters) {
            if (disasterType.equals(notificationDisaster.getDisasterType()))
                return true;
        }
        return false;
    }

    public SituationHomeResponse getSituationHome() {
        Set<Disaster> disasters = new HashSet<>();
        long id = SecurityContextProvider.getAuthenticatedUserId();
        Member member = memberRepository.findById(id)
                .orElseThrow(NotFoundMemberException::new);
        LocalDateTime time = LocalDateTime.now().minusDays(1);
        for (NotificationRegion notificationRegion : member.getNotificationRegions()) {
            disasters.addAll(disasterRepository.findDisastersInAddressAfterTime(notificationRegion.getLocation(), time));
        }
        disasters.removeIf(disaster -> !isValidDisasterType(disaster.getDisasterType(), member.getNotificationDisasters()));


        List<SituationResponse> situationResponses = new ArrayList<>();
        for (Disaster disaster : disasters) {
            Long conversationCnt = 0L;
            List<GetConversationResponse> conversationResponses = new ArrayList<>();
            conversationCnt += conversationRepository.countByDisaster(disaster);
            List<Conversation> conversations = conversationRepository.findAllByDisasterOrderByLikeCntDesc(disaster, PageRequest.of(0, 3));
            for (Conversation conversation : conversations) {
                conversationResponses.add(conversationService.getExceptChild(conversation.getId()));
                conversationCnt += conversationRepository.countByParent(conversation);
            }
            situationResponses.add(SituationResponse.of(disaster, conversationResponses, conversationCnt));
        }

        return SituationHomeResponse.of(situationResponses);
    }

    public SituationDetailResponse getSituationDetail(Long disasterId, String sort) {
        Disaster disaster = disasterRepository.findById(disasterId)
                .orElseThrow(NotFoundDisasterException::new);
        List<GetConversationResponse> conversationResponses = new ArrayList<>();
        List<Conversation> conversations;
        if (sort.equals("popularity"))
            conversations = conversationRepository.findAllByDisasterOrderByLikeCntDesc(disaster);
        else if (sort.equals("time"))
            conversations = conversationRepository.findAllByDisasterOrderByCreatedAtDesc(disaster);
        else
            throw new BadRequestConversationSortException();
        for (Conversation conversation : conversations) {
            conversationResponses.add(conversationService.get(conversation.getId()));
        }
        return SituationDetailResponse.of(conversationResponses);
    }
}
