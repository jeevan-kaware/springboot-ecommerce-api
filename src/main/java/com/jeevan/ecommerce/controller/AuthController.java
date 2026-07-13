package com.jeevan.ecommerce.controller;

import com.jeevan.ecommerce.dto.request.ChangePasswordRequest;
import com.jeevan.ecommerce.dto.request.LoginRequest;
import com.jeevan.ecommerce.dto.request.RefreshTokenRequest;
import com.jeevan.ecommerce.dto.request.RegisterRequest;
import com.jeevan.ecommerce.dto.response.AuthResponse;
import com.jeevan.ecommerce.entity.RefreshToken;
import com.jeevan.ecommerce.entity.User;
import com.jeevan.ecommerce.service.RefreshTokenService;
import com.jeevan.ecommerce.service.AuthService;
import com.jeevan.ecommerce.security.jwt.JwtService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;


    @PostMapping("/register")
    public User register(
            @Valid @RequestBody RegisterRequest request
    ){

        return authService.register(request);

    }



    @PostMapping("/login")
    public AuthResponse login(
            @Valid @RequestBody LoginRequest request
    ){

        return authService.login(request);

    }



    @PostMapping("/refresh")
    public AuthResponse refreshToken(
            @RequestBody RefreshTokenRequest request
    ){

        RefreshToken refreshToken =
                refreshTokenService.verifyRefreshToken(
                        request.getRefreshToken()
                );


        String accessToken =
                jwtService.generateToken(
                        refreshToken.getEmail()
                );


        return new AuthResponse(
                accessToken,
                refreshToken.getToken()
        );

    }



    @PostMapping("/logout")
    public String logout(
           @Valid @RequestBody RefreshTokenRequest request
    ){

        refreshTokenService.deleteByToken(
                request.getRefreshToken()
        );


        return "Logout Successfully";

    }
    @PutMapping("/change-password")
    public String changePassword(
            Authentication authentication,
           @Valid @RequestBody ChangePasswordRequest request) {

        return authService.changePassword(
                authentication.getName(),
                request);
    }

}