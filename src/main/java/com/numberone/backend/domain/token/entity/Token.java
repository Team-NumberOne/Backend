package com.numberone.backend.domain.token.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Getter
@RedisHash(value = "token", timeToLive = 60 * 60 * 24 * 14)//@Entity가 RDBMS에 테이블을 생성했다면 @RedisHash는 Redis에 테이블(?)을 생성
public class Token implements Serializable {
    @Id
    private String email;

    @Indexed//특정 컬럼을 인덱스(보조 id)로 지정하여 효율적인 검색이 가능하게 함
    private String accessToken;

    private String refreshToken;

    @Builder
    public Token(String email, String accessToken, String refreshToken) {
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static Token of(String email, String accessToken, String refreshToken){
        return Token.builder()
                .email(email)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void updateAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
