package com.numberone.backend.domain.member.dto.response;

import lombok.*;

@ToString
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UploadProfileImageResponse {

    private String imageUrl;

    public static UploadProfileImageResponse of(String imageUrl) {
        return UploadProfileImageResponse.builder()
                .imageUrl(imageUrl)
                .build();
    }
}
