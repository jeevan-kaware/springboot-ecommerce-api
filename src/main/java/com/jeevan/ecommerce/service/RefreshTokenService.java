package com.jeevan.ecommerce.service;

import com.jeevan.ecommerce.entity.RefreshToken;
import com.jeevan.ecommerce.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repository;

    public RefreshToken createRefreshToken(String email) {

        RefreshToken token = RefreshToken.builder()
                .email(email)
                .token(UUID.randomUUID().toString())
                .expiryDate(
                        new Date(System.currentTimeMillis()
                                + 1000L * 60 * 60 * 24 * 7)
                )
                .build();

        return repository.save(token);
    }

    public boolean isValid(RefreshToken token) {
        return token.getExpiryDate().after(new Date());
    }

    @Transactional
    public void deleteByToken(String token) {
        repository.deleteByToken(token);
    }

    public RefreshToken verifyRefreshToken(String token) {

        RefreshToken refreshToken = repository.findByToken(token)
                .orElseThrow(() ->
                        new RuntimeException("Refresh Token Not Found"));

        if (!isValid(refreshToken)) {

            repository.delete(refreshToken);

            throw new RuntimeException("Refresh Token Expired");
        }

        return refreshToken;
    }
}