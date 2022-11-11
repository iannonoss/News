package com.example.news.service;


import com.example.news.dto.RefreshTokenRequest;
import com.example.news.entity.RefreshToken;
import com.example.news.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(User user);

    UserDetails getUserDetailsFromToken(RefreshTokenRequest refreshTokenRequest);
}
