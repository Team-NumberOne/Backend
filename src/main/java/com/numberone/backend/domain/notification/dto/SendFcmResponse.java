package com.numberone.backend.domain.notification.dto;

import lombok.*;

@ToString
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SendFcmResponse {
    private String msg;
}
