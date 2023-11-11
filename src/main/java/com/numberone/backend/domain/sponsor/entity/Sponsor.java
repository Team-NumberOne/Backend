package com.numberone.backend.domain.sponsor.entity;

import com.numberone.backend.domain.disaster.util.DisasterType;
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
    private DisasterType disasterType;

    @Comment("제목")
    private String title;

    @Comment("세부 내용")
    private String content;

    @Comment("시작 날짜")
    private LocalDate startDate;

    @Comment("마감 날짜")
    private LocalDate dueDate;

    @Comment("후원 받을 마음 목표치")
    private Integer heartTarget;

    @Comment("후원사 이름")
    private String sponsorName;

    @OneToMany(mappedBy = "sponsor", cascade = CascadeType.ALL)
    private List<Support> supports = new ArrayList<>();
}
