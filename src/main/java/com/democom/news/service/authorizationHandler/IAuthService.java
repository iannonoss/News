package com.democom.news.service.authorizationHandler;

import com.democom.news.dto.*;
import com.democom.news.entity.User;

public interface IAuthService {



    LoginResponse login(AuthModel authModel);
    String refreshToken(RefreshTokenRequest refreshTokenRequest);

    String forgotPassword(ForgotPasswordRequest email);


    ResetPasswordResponse resetPassword(String token, ResetPasswordRequest passwordRequest);

    Boolean isPasswordTokenValid(String code);
}
