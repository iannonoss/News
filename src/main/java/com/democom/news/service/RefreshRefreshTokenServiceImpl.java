package com.democom.news.service;

import com.democom.news.entity.User;
import com.democom.news.exception.ResourceNotFoundException;
import com.democom.news.security.AuthService;
import com.democom.news.dto.RefreshTokenRequest;
import com.democom.news.entity.RefreshToken;
import com.democom.news.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RefreshRefreshTokenServiceImpl implements IRefreshTokenService {

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
