package com.numberone.backend.domain.sponsor.dto.response;

import com.numberone.backend.domain.sponsor.entity.Sponsor;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(defaultValue = "1")
    private Long id;

    @Schema(defaultValue = "true")
    private Boolean isSupported;

    @Schema(defaultValue = "화재")
    private String disasterType;

    @Schema(defaultValue = "D-3")
    private String dday;

    @Schema(defaultValue = "제목")
    private String title;

    @Schema(defaultValue = "부제목")
    private String subtitle;

    @Schema(defaultValue = "세부 후원 페이지 내용")
    private String content;

    @Schema(defaultValue = "100")
    private Integer targetHeart;

    @Schema(defaultValue = "30")
    private Integer currentHeart;

    @Schema(defaultValue = "사회복지재단 월드비전")
    private String sponsorName;

    @Schema(defaultValue = "2018.11.12 ~ 2018.11.15")
    private String period;

    @Schema(defaultValue = "https://numberone-dev-s3.s3.ap-northeast-2.amazonaws.com/static/sponsor_image.png")
    private String thumbnailUrl;

    @Schema(defaultValue = "https://www.naver.com")
    private String imageUrl;

    @Schema(defaultValue = "https://numberone-dev-s3.s3.ap-northeast-2.amazonaws.com/static/sponsor_thumbnail.png")
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
                .thumbnailUrl(sponsor.getThumbnailUrl())
                .imageUrl(sponsor.getImageUrl())
                .sponsorUrl(sponsor.getSponsorUrl())
                .build();
    }
}
