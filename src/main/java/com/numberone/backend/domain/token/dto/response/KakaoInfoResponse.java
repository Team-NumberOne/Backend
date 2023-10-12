package com.numberone.backend.domain.token.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class KakaoInfoResponse {
    private Long id;
    private String connected_at;
    private Properties properties;
    private KakaoAccount kakao_account;

    @ToString
    @Getter
    @NoArgsConstructor
    public class Properties {
        private String nickname;
        private String profile_image;
        private String thumbnail_image;
    }

    @ToString
    @Getter
    @NoArgsConstructor
    public class KakaoAccount {
        static class profile {
            private String nickname;
            private String thumbnail_image_url;
            private String profile_image_url;
            private String is_default_image;
        }

        private Boolean profile_nickname_needs_agreement;
        private Boolean profile_image_needs_agreement;
        private profile profile;
        private Boolean has_email;
        private Boolean email_needs_agreement;
        private Boolean is_email_valid;
        private Boolean is_email_verified;
        private String email;
    }
}



