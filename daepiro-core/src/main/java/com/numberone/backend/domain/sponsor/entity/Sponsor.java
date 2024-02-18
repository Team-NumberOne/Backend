package com.numberone.backend.domain.sponsor.entity;

import com.numberone.backend.domain.disaster.DisasterType;
import com.numberone.backend.domain.support.entity.Support;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Sponsor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("재난 유형")
    @Enumerated(EnumType.STRING)
    private DisasterType disasterType;

    @Comment("제목")
    private String title;

    @Comment("부제목")
    private String subtitle;

    @Lob
    @Column(columnDefinition = "TEXT")
    @Comment("내용")
    private String content;

    @Comment("시작 날짜")
    private LocalDate startDate;

    @Comment("마감 날짜")
    private LocalDate dueDate;

    @Comment("후원 받을 마음 목표치")
    private Integer targetHeart;

    @Comment("현재 후원받은 마음")
    private Integer currentHeart;

    @Comment("후원사 이름")
    private String sponsorName;

    @Comment("썸네일 이미지")
    private String thumbnailUrl;

    @Comment("상세페이지 이미지")
    private String imageUrl;

    @Comment("후원사 링크")
    private String sponsorUrl;

    @OneToMany(mappedBy = "sponsor", cascade = CascadeType.ALL)
    private List<Support> supports = new ArrayList<>();

    public void increaseHeart(int heart) {
        currentHeart += heart;
    }
}
