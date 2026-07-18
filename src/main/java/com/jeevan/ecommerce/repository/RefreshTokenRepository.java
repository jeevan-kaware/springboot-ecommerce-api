package com.jeevan.ecommerce.repository;

import com.jeevan.ecommerce.entity.Product;
import com.jeevan.ecommerce.entity.RefreshToken;
import com.jeevan.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);
    void deleteByToken(String token);

}