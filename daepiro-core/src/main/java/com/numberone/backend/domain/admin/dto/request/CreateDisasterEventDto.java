package com.numberone.backend.domain.admin.dto.request;

import com.numberone.backend.domain.disaster.util.DisasterType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateDisasterEventDto {
    DisasterType type;
    String location;
    String message;
    Long disasterNum;
}
