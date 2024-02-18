package com.numberone.backend.domain.disaster.event;

import com.numberone.backend.domain.disaster.entity.Disaster;
import com.numberone.backend.domain.disaster.DisasterType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter
@AllArgsConstructor
public class DisasterEvent {
    DisasterType type;
    String location;
    String message;
    Long disasterNum;
    public static DisasterEvent of (Disaster disaster){
        return DisasterEvent.builder()
                .type(disaster.getDisasterType())
                .location(disaster.getLocation())
                .message(disaster.getMsg())
                .disasterNum(disaster.getDisasterNum())
                .build();
    }
}
