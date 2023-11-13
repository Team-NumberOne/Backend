package com.numberone.backend.domain.comment.dto.request;

import com.numberone.backend.config.basetime.BaseTimeEntity;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@ToString
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateCommentRequest {

    @NotNull
    private String content;

    private Double longitude;
    private Double latitude;

}
