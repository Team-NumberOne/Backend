package com.numberone.backend.domain.support.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateSupportRequest {
    @NotNull(message = "어느 것에 후원할지 sponsor id를 입력해주세요")
    private Long sponsorId;
    @NotNull(message = "후원할 마음 갯수를 입력해주세요")
    private Integer heartCnt;
}
