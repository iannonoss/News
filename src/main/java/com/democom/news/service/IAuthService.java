package com.democom.news.service;

import com.democom.news.dto.AuthModel;
import com.democom.news.dto.LoginResponse;
import com.democom.news.dto.RefreshTokenRequest;

public interface IAuthService {


    LoginResponse login(AuthModel authModel) throws Exception;
    String refreshToken(RefreshTokenRequest refreshTokenRequest);



}
