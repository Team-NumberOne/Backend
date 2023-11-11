package com.numberone.backend.domain.disaster.entity;

import com.numberone.backend.domain.disaster.util.DisasterType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Comment("재난 정보")
public class Disaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("재난 유형")
    @Enumerated(EnumType.STRING)
    private DisasterType disasterType;

    @Comment("위치")
    private String location;

    @Comment("메시지 내용")
    private String msg;

    @Comment("국민재난안전포털에서 배정된 재난 번호")
    private Long disasterNum;

    @Comment("재난 발생 시각")
    private LocalDateTime generatedAt;

    @Builder
    public Disaster(DisasterType disasterType, String location, String msg, Long disasterNum, LocalDateTime generatedAt) {
        this.disasterType = disasterType;
        this.location = location;
        this.msg = msg;
        this.disasterNum = disasterNum;
        this.generatedAt = generatedAt;
    }

    @Builder
    public static Disaster of(DisasterType disasterType, String location, String msg, Long disasterNum, LocalDateTime generatedAt) {
        return Disaster.builder()
                .disasterType(disasterType)
                .location(location)
                .msg(msg)
                .disasterNum(disasterNum)
                .generatedAt(generatedAt)
                .build();
    }
}