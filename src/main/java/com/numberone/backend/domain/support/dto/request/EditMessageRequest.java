package com.numberone.backend.domain.support.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EditMessageRequest {
    @NotEmpty(message = "응원 메시지 내용이 비어있습니다.")
    public String message;
}
