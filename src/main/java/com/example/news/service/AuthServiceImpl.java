package com.example.news.service;

import com.example.news.dto.AuthModel;
import com.example.news.dto.LoginResponse;
import com.example.news.dto.RefreshTokenRequest;
import com.example.news.entity.Author;
import com.example.news.entity.JwtResponse;
import com.example.news.entity.RefreshToken;
import com.example.news.entity.User;
import com.example.news.security.AuthService;
import com.example.news.security.CustomUserDetails;
import com.example.news.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements IAuthService{



    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public LoginResponse login(AuthModel authModel) throws Exception {
        authenticate(authModel.getEmail(), authModel.getPassword());

        final UserDetails userDetails =  userDetailsService.loadUserByUsername(authModel.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);


        User user = userService.getUserFromEmail(authModel.getEmail());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setJwt(new JwtResponse(token).getJwtToken());
        loginResponse.setRefreshToken(refreshToken.getRefreshToken());

        return loginResponse;
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e){
            throw new Exception("User disabled");
        } catch (BadCredentialsException e) {
            throw new Exception("Bad credentials");
        }
    }

    @Override
    public String refreshToken(RefreshTokenRequest refreshTokenRequest) {
        UserDetails userDetails =refreshTokenService.getUserDetailsFromToken(refreshTokenRequest);
        return jwtTokenUtil.generateToken(userDetails);
    }


}
