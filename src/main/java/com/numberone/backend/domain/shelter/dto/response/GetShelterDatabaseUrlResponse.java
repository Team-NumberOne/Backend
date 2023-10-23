package com.numberone.backend.domain.shelter.dto.response;

import lombok.*;

@ToString
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GetShelterDatabaseUrlResponse {
    String link;

    public static GetShelterDatabaseUrlResponse of(String link) {
        return GetShelterDatabaseUrlResponse.builder()
                .link(link)
                .build();
    }
}
