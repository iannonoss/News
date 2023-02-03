package com.democom.news.service.authorizationHandler;

import com.democom.news.dto.*;
import com.democom.news.entity.User;
import com.democom.news.entity.JwtResponse;
import com.democom.news.entity.RefreshToken;
import com.democom.news.exception.ResourceNotFoundException;
import com.democom.news.exception.UnauthorizedException;
import com.democom.news.exception.UnauthorizedInstructionException;
import com.democom.news.security.AuthService;
import com.democom.news.service.EmailService;
import com.democom.news.util.JwtTokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;


@Service
public class AuthServiceImpl implements IAuthService {



    @Autowired
    private com.democom.news.service.refreshTokenHandler.IRefreshTokenService IRefreshTokenService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthService userDetailsService;

    @Autowired
    private com.democom.news.service.IUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${password-token.validity}")
    private int passwordTokenValidity;



    @Override
    public LoginResponse login(AuthModel authModel) {
        authenticate(authModel.getEmail(), authModel.getPassword());

         UserDetails userDetails =  userDetailsService.loadUserByUsername(authModel.getEmail());
         String token = jwtTokenUtil.generateToken(userDetails);

        User user = userService.getUserFromEmail(authModel.getEmail());
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

    @Override
    public String forgotPassword(ForgotPasswordRequest email) {
           User user = userService.getUserFromEmail(email.getEmail());
           user.setResetPasswordCode(UUID.randomUUID().toString());
            user.setExpiredDate(new Date(System.currentTimeMillis() + passwordTokenValidity));
            userService.saveUser(user);
            EmailDetails details = new EmailDetails();
            details.setRecipient(user.getEmail());
            details.setMsgBody("Go on http://localhost:4000/auth/reset-password/"+user.getResetPasswordCode()+" to reset password");
            details.setSubject("Reset password");
        return emailService.sendLink(details);

    }


    @Override
    public ResetPasswordResponse resetPassword(String token, ResetPasswordRequest passwordRequest) {
         ResetPasswordResponse userResetting  = new ResetPasswordResponse();
        if(!isPasswordTokenValid(token)){
            throw new UnauthorizedInstructionException("Password token " + token + " expired");
        }

        if(userService.getUserFromPasswordToken(token).isPresent()) {
            User user = userService.getUserFromPasswordToken(token).get();
            user.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
            userService.saveUser(user);
            BeanUtils.copyProperties(user, userResetting);
            return userResetting;
        }
        else throw new ResourceNotFoundException("User not found with code: " + token);
    }


    @Override
    public Boolean isPasswordTokenValid(String code) {
        Optional<User> user = userService.getUserFromPasswordToken(code);
        return user.isPresent() && !user.get().getExpiredDate().before(new Date());
    }



    //TODO: recupera password & logout + test


}
