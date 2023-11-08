package com.numberone.backend.domain.disaster.dto.event;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DisasterEvent {
    private LocalDateTime createDate;
    private Long locationId;
    private String locationName;
    private Long msgId;
    private String msg;
}
