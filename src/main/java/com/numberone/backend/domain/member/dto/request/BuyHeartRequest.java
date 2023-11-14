package com.numberone.backend.domain.member.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BuyHeartRequest {
    @NotNull(message = "구입한 마음 갯수를 입력해주세요")
    private Integer heartCnt;
}
