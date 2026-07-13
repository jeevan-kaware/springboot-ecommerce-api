package com.jeevan.ecommerce.service;

import com.jeevan.ecommerce.dto.request.ChangePasswordRequest;
import com.jeevan.ecommerce.dto.request.LoginRequest;
import com.jeevan.ecommerce.dto.request.RegisterRequest;
import com.jeevan.ecommerce.dto.response.AuthResponse;
import com.jeevan.ecommerce.entity.RefreshToken;
import com.jeevan.ecommerce.entity.User;
import com.jeevan.ecommerce.enums.Role;
import com.jeevan.ecommerce.exception.ResourceNotFoundException;
import com.jeevan.ecommerce.repository.UserRepository;
import com.jeevan.ecommerce.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.jeevan.ecommerce.exception.DuplicateResourceException;
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;

    public User register(RegisterRequest request) {

        if (repository.existsByEmail(request.getEmail())) {
             throw new DuplicateResourceException("Email already exists");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        return repository.save(user);
    }

    public AuthResponse login(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        if (authentication.isAuthenticated()) {

            String accessToken =
                    jwtService.generateToken(request.getEmail());

            RefreshToken refreshToken =
                    refreshTokenService.createRefreshToken(
                            request.getEmail()
                    );

            return new AuthResponse(
                    accessToken,
                    refreshToken.getToken()
            );
        }

        throw new RuntimeException("Invalid Email or Password");
    }

    public String changePassword(
            String email,
            ChangePasswordRequest request) {

        User user = repository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(
                request.getOldPassword(),
                user.getPassword())) {

            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(
                passwordEncoder.encode(request.getNewPassword()));

        repository.save(user);

        return "Password changed successfully";
    }
}