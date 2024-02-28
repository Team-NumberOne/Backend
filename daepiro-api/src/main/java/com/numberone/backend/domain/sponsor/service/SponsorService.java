package com.numberone.backend.domain.sponsor.service;

import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.member.repository.MemberRepository;
import com.numberone.backend.domain.sponsor.dto.response.SponsorHomeResponse;
import com.numberone.backend.domain.sponsor.dto.response.SponsorResponse;
import com.numberone.backend.domain.sponsor.entity.Sponsor;
import com.numberone.backend.domain.sponsor.repository.SponsorRepository;
import com.numberone.backend.domain.support.entity.Support;
import com.numberone.backend.domain.support.repository.SupportRepository;
import com.numberone.backend.exception.notfound.NotFoundMemberException;
import com.numberone.backend.exception.notfound.NotFoundSponsorException;
import com.numberone.backend.provider.security.SecurityContextProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SponsorService {
    private final SponsorRepository sponsorRepository;
    private final MemberRepository memberRepository;
    private final SupportRepository supportRepository;

    public SponsorResponse getSponsorDetail(Long sponsorId) {
        Sponsor sponsor = sponsorRepository.findById(sponsorId)
                .orElseThrow(NotFoundSponsorException::new);
        return SponsorResponse.of(
                null,
                sponsor
        );
    }

    public SponsorHomeResponse getSponsorHomeLatest() {
        List<SponsorResponse> sponsorResponses = new ArrayList<>();
        long id = SecurityContextProvider.getAuthenticatedUserId();
        Member member = memberRepository.findById(id)
                .orElseThrow(NotFoundMemberException::new);
        List<Sponsor> sponsors = sponsorRepository.findAllByOrderByStartDateDesc();
        for (Sponsor sponsor : sponsors) {
            boolean isSupported = false;
            for (Support support : sponsor.getSupports()) {
                if (support.getMember().getId().equals(member.getId())) {
                    isSupported = true;
                    break;
                }
            }
            sponsorResponses.add(SponsorResponse.of(isSupported, sponsor));
        }

        List<String> messages = new ArrayList<>();
        List<Support> supports = supportRepository.findAll();
        for (Support support : supports) {
            if (support.getMessage() != null)
                messages.add(support.getMessage());
        }

        return SponsorHomeResponse.of(
                supportRepository.getSupportCnt(),
                messages,
                member.getNickName(),
                sponsorResponses
        );
    }

    public SponsorHomeResponse getSponsorHomePopular() {
        List<SponsorResponse> sponsorResponses = new ArrayList<>();
        long id = SecurityContextProvider.getAuthenticatedUserId();
        Member member = memberRepository.findById(id)
                .orElseThrow(NotFoundMemberException::new);
        List<Sponsor> sponsors = sponsorRepository.findAllByOrderByCurrentHeartDesc();
        for (Sponsor sponsor : sponsors) {
            boolean isSupported = false;
            for (Support support : sponsor.getSupports()) {
                if (support.getMember().getId().equals(member.getId())) {
                    isSupported = true;
                    break;
                }
            }
            sponsorResponses.add(SponsorResponse.of(isSupported, sponsor));
        }

        List<String> messages = new ArrayList<>();
        List<Support> supports = supportRepository.findAll();
        for (Support support : supports) {
            if (support.getMessage() != null)
                messages.add(support.getMessage());
        }

        return SponsorHomeResponse.of(
                supportRepository.getSupportCnt(),
                messages,
                member.getNickName(),
                sponsorResponses
        );
    }
}
