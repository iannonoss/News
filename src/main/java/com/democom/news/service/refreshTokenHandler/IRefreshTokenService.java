package com.democom.news.service.refreshTokenHandler;


import com.democom.news.dto.RefreshTokenRequest;
import com.democom.news.entity.RefreshToken;
import com.democom.news.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface IRefreshTokenService {

    RefreshToken createRefreshToken(User user);

    UserDetails getUserDetailsFromToken(RefreshTokenRequest refreshTokenRequest);
}
