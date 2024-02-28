package com.numberone.backend.domain.token.service;

import com.numberone.backend.domain.token.entity.RefreshToken;
import com.numberone.backend.domain.token.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public void update(long userId, String token) {
        if (refreshTokenRepository.existsById(userId))
            refreshTokenRepository.deleteById(userId);
        refreshTokenRepository.save(RefreshToken.of(userId, token));
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }
}
