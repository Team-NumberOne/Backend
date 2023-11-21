package com.numberone.backend.domain.admin.dto.request;

import com.numberone.backend.domain.disaster.util.DisasterType;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateDisasterEventDto {
    DisasterType type;
    String location;
    String message;
    Long disasterNum;
}
