package com.numberone.backend.domain.sponsor.dto.response;

import com.numberone.backend.domain.sponsor.entity.Sponsor;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SponsorResponse {
    private Long id;
    private Boolean isSupported;
    private String disasterType;
    private String dday;
    private String title;
    private String subtitle;
    private String content;
    private Integer targetHeart;
    private Integer currentHeart;
    private String sponsorName;
    private String period;
    private String thumbnail;
    private String image;
    private String sponsorUrl;

    public static SponsorResponse of(Boolean isSupported, Sponsor sponsor) {
        int dday = Period.between(LocalDate.now(), sponsor.getDueDate()).getDays();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy. MM. dd");
        String startDate = sponsor.getStartDate().format(formatter);
        String dueDate = sponsor.getDueDate().format(formatter);

        return SponsorResponse.builder()
                .id(sponsor.getId())
                .isSupported(isSupported)
                .disasterType(sponsor.getDisasterType().getDescription())
                .dday("D-" + dday)
                .title(sponsor.getTitle())
                .subtitle(sponsor.getSubtitle())
                .content(sponsor.getContent())
                .targetHeart(sponsor.getTargetHeart())
                .currentHeart(sponsor.getCurrentHeart())
                .sponsorName(sponsor.getSponsorName())
                .period(startDate + " ~ " + dueDate)
                .thumbnail(sponsor.getThumbnail())
                .image(sponsor.getImage())
                .sponsorUrl(sponsor.getSponsorUrl())
                .build();
    }
}
