package com.numberone.backend.domain.notification.service;

import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.member.repository.MemberRepository;
import com.numberone.backend.domain.notification.dto.parameter.NotificationSearchParameter;
import com.numberone.backend.domain.notification.dto.response.NotificationTabResponse;
import com.numberone.backend.domain.notification.repository.NotificationRepository;
import com.numberone.backend.exception.notfound.NotFoundMemberException;
import com.numberone.backend.provider.security.SecurityContextProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {
    private final MemberRepository memberRepository;
    private final NotificationRepository notificationRepository;

    public Slice<NotificationTabResponse> getNotificationTabPagesByMember(NotificationSearchParameter param, Pageable pageable) {
        long principal = SecurityContextProvider.getAuthenticatedUserId();
        Member member = memberRepository.findById(principal)
                .orElseThrow(NotFoundMemberException::new);
        return notificationRepository.getNotificationTabPagesNoOffSetByMember(param, member.getId(), pageable);
    }

}
