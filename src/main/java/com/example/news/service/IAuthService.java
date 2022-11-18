package com.example.news.service;

import com.example.news.dto.AuthModel;
import com.example.news.dto.LoginResponse;
import com.example.news.dto.RefreshTokenRequest;

public interface IAuthService {


    LoginResponse login(AuthModel authModel) throws Exception;
    String refreshToken(RefreshTokenRequest refreshTokenRequest);



}
