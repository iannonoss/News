package com.example.news.service;

import com.example.news.dto.RefreshTokenRequest;
import com.example.news.entity.RefreshToken;
import com.example.news.entity.User;
import com.example.news.exception.ResourceNotFoundException;
import com.example.news.repository.RefreshTokenRepository;
import com.example.news.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RefreshRefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private AuthService userDetailsService;


    @Override
    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(user.getId()).orElse(new RefreshToken());
        if(refreshToken.getRefreshToken() == null) {
            refreshToken.setUser(user);
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        }

        return refreshTokenRepository.save(refreshToken);
    }


    @Override
    public UserDetails getUserDetailsFromToken(RefreshTokenRequest refreshTokenRequest){
        String email = findUserByRefreshToken(refreshTokenRequest.getRefreshToken());
        return  userDetailsService.loadUserByUsername(email);
    }

    private String findUserByRefreshToken(String token){
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token error, not found in the DB"));
        return refreshToken.getUser().getEmail();
    }
}
