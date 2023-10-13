package com.numberone.backend.domain.token.repository;

import com.numberone.backend.domain.token.entity.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<Token,String> {
    Optional<Token> findByAccessToken(String accessToken);
}
