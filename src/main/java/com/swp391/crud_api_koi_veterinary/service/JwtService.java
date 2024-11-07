package com.swp391.crud_api_koi_veterinary.service;

import org.springframework.security.core.Authentication;

import jakarta.servlet.http.HttpServletRequest;

public interface JwtService {
    String generateAccessToken(Authentication authentication);

    String getUsernameFromJWT(String token);

    boolean validateToken(String authToken);

    String getJwtFromRequest(HttpServletRequest request);
}
