package com.numberone.backend.domain.disaster.entity;

import com.numberone.backend.domain.disaster.util.DisasterType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Disaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DisasterType disasterType;

    private String location;

    private String msg;

    private Long disasterNum;

    private LocalDateTime createdAt;

    @Builder
    public Disaster(DisasterType disasterType, String location, String msg, Long disasterNum, LocalDateTime createdAt) {
        this.disasterType = disasterType;
        this.location = location;
        this.msg = msg;
        this.disasterNum = disasterNum;
        this.createdAt = createdAt;
    }

    @Builder
    public static Disaster of(DisasterType disasterType, String location, String msg, Long disasterNum, LocalDateTime createdAt) {
        return Disaster.builder()
                .disasterType(disasterType)
                .location(location)
                .msg(msg)
                .disasterNum(disasterNum)
                .createdAt(createdAt)
                .build();
    }
}
