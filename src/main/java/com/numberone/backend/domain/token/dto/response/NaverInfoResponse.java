package com.numberone.backend.domain.token.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverInfoResponse {
    private String resultcode;
    private String message;
    private Response response;

    @ToString
    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Response {
        private String id;
        private String nickname;
        private String email;
        private String name;
    }
}
