package com.numberone.backend.domain.support.service;

import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.member.repository.MemberRepository;
import com.numberone.backend.domain.sponsor.entity.Sponsor;
import com.numberone.backend.domain.sponsor.repository.SponsorRepository;
import com.numberone.backend.domain.support.dto.request.CreateSupportRequest;
import com.numberone.backend.domain.support.dto.request.EditMessageRequest;
import com.numberone.backend.domain.support.dto.response.CreateSupportResponse;
import com.numberone.backend.domain.support.entity.Support;
import com.numberone.backend.domain.support.repository.SupportRepository;
import com.numberone.backend.exception.notfound.NotFoundMemberException;
import com.numberone.backend.exception.notfound.NotFoundSupportException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SupportService {
    private final SupportRepository supportRepository;
    private final SponsorRepository sponsorRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void editMessage(EditMessageRequest editMessageRequest, Long supportId) {
        Support support = supportRepository.findById(supportId)
                .orElseThrow(NotFoundSupportException::new);
        support.setMessage(editMessageRequest.getMessage());
    }

    @Transactional
    public CreateSupportResponse create(CreateSupportRequest createSupportRequest, String email) {
        Sponsor sponsor = sponsorRepository.findById(createSupportRequest.getSponsorId())
                .orElseThrow(NotFoundSupportException::new);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(NotFoundMemberException::new);
        Support support = Support.of(
                null,
                sponsor,
                member
        );
        sponsor.increaseHeart(createSupportRequest.getHeartCnt());
        support = supportRepository.save(support);
        return CreateSupportResponse.of(support.getId());
    }
}
