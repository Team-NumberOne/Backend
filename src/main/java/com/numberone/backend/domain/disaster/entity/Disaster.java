package com.numberone.backend.domain.disaster.entity;

import com.numberone.backend.domain.disaster.util.DisasterType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Disaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DisasterType disasterType;

    private Integer severity;

    private String title;

    private String msg;

    private String info;

    @Builder
    public Disaster(DisasterType disasterType, Integer severity, String title, String msg, String info) {
        this.disasterType = disasterType;
        this.severity = severity;
        this.title = title;
        this.msg = msg;
        this.info = info;
    }

    public static Disaster of(DisasterType disasterType, Integer severity, String title, String msg, String info) {
        return Disaster.builder()
                .disasterType(disasterType)
                .severity(severity)
                .title(title)
                .msg(msg)
                .info(info)
                .build();
    }
}
