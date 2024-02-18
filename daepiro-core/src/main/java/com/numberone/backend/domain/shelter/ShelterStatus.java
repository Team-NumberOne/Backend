package com.numberone.backend.domain.shelter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ShelterStatus {
    OPEN, // 정상 운영
    CLOSE;  // 운영 중단
    String value;
}
