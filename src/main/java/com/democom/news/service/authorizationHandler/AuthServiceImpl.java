package com.democom.news.service.authorizationHandler;

import com.democom.news.entity.User;
import com.democom.news.dto.AuthModel;
import com.democom.news.dto.LoginResponse;
import com.democom.news.dto.RefreshTokenRequest;
import com.democom.news.entity.JwtResponse;
import com.democom.news.entity.RefreshToken;
import com.democom.news.exception.UnauthorizedException;
import com.democom.news.security.AuthService;
import com.democom.news.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements IAuthService {



    @Autowired
    private com.democom.news.service.refreshTokenHandler.IRefreshTokenService IRefreshTokenService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthService userDetailsService;

    @Autowired
    private com.democom.news.service.IUserService IUserService;

    @Autowired
    private AuthenticationManager authenticationManager;



    @Override
    public LoginResponse login(AuthModel authModel) {
        authenticate(authModel.getEmail(), authModel.getPassword());

         UserDetails userDetails =  userDetailsService.loadUserByUsername(authModel.getEmail());
         String token = jwtTokenUtil.generateToken(userDetails);

        User user = IUserService.getUserFromEmail(authModel.getEmail());
        RefreshToken refreshToken = IRefreshTokenService.createRefreshToken(user);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setJwt(new JwtResponse(token).getJwtToken());
        loginResponse.setRefreshToken(refreshToken.getRefreshToken());

        return loginResponse;
    }

    private void authenticate(String email, String password) throws UnauthorizedException, BadCredentialsException{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (UnauthorizedException e){
            throw new UnauthorizedException("User disabled");
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @Override
    public String refreshToken(RefreshTokenRequest refreshTokenRequest) {
        UserDetails userDetails = IRefreshTokenService.getUserDetailsFromToken(refreshTokenRequest);
        return jwtTokenUtil.generateToken(userDetails);
    }

    //TODO: recupera password & logout + test


}
