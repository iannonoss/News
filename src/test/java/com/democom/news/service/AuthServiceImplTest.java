package com.democom.news.service;

import com.democom.news.dto.AuthModel;
import com.democom.news.dto.LoginResponse;
import com.democom.news.dto.RefreshTokenRequest;
import com.democom.news.service.authorizationHandler.IAuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(scripts = "init-db.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class AuthServiceImplTest {

    @Autowired
    private IAuthService authService;

    @Test
    @DisplayName("Valid login")
    void login() {
        AuthModel authUser = takeValidUser();

        LoginResponse loginResponse = null;

        try {
            loginResponse = authService.login(authUser);
        } catch (Exception e) {
            assert false;
            throw new RuntimeException(e);
        }
        assertNotNull(loginResponse);
    }


    @Test
    @DisplayName("invalid login")
    void login_w_invalid_user() {

        AuthModel notAuthUser = takeInvalidUser();
        assertThrowsExactly(BadCredentialsException.class, () -> authService.login(notAuthUser));
    }

    @Test
    @DisplayName("Refresh JWT token")
    void refreshToken() {
        AuthModel authModel = takeValidUser();
        LoginResponse loginResponse = authService.login(authModel);

        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
        refreshTokenRequest.setRefreshToken(loginResponse.getRefreshToken());

        String newJwt = authService.refreshToken(refreshTokenRequest);

        assertAll(
                () -> assertNotNull(newJwt),
                () -> assertNotEquals(loginResponse.getJwt(), newJwt)
        );
    }

    private AuthModel takeValidUser(){
        AuthModel authorizedUser = new AuthModel();
        authorizedUser.setEmail("g.bianchi@gmail.com");
        authorizedUser.setPassword("password");
        return authorizedUser;
    }

    private AuthModel takeInvalidUser(){
        AuthModel unauthorizedUser = new AuthModel();
        unauthorizedUser.setEmail("f.verdi@gmail.com");
        unauthorizedUser.setPassword("password3");

        return  unauthorizedUser;
    }
}