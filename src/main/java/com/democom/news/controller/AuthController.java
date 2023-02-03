package com.democom.news.controller;


import com.democom.news.dto.*;
import com.democom.news.dto.enums.ERole;
import com.democom.news.entity.Author;
import com.democom.news.entity.Reader;
import com.democom.news.entity.User;
import com.democom.news.service.authorizationHandler.IAuthService;
import com.democom.news.entity.JwtResponse;
import com.democom.news.service.readerHandler.IReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    private IReaderService readerService;

    @Autowired
    private com.democom.news.service.auhtorHandler.IAuthorService IAuthorService;

    @Autowired
    private IAuthService authService;



    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody AuthModel authModel) throws Exception {
        return new ResponseEntity<>(authService.login(authModel), HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public HttpStatus forgotPassword(@RequestBody ForgotPasswordRequest request) {
        authService.forgotPassword(request);
        return HttpStatus.OK;
    }

    @PostMapping("/reset-password/{token}")
    public ResponseEntity<ResetPasswordResponse> resetPassword(@RequestBody ResetPasswordRequest passwordRequest, @PathVariable String token){
        return new ResponseEntity<>(authService.resetPassword(token, passwordRequest), HttpStatus.OK);
    }

    @PostMapping("/register-user")
    public ResponseEntity<Reader> saveUser(@Valid @RequestBody UserModel userModel){
        return new ResponseEntity<Reader>(readerService.createReader(userModel, ERole.ROLE_USER ), HttpStatus.CREATED);
    }
    @PostMapping("/register-mod")
    public ResponseEntity<Reader> saveMod(@Valid @RequestBody UserModel userModel){
        return new ResponseEntity<Reader>(readerService.createReader(userModel, ERole.ROLE_MODERATOR ), HttpStatus.CREATED);
    }

    @PostMapping("/register-author")
    public ResponseEntity<Author> saveAuthor(@Valid @RequestBody AuthorModel authorModel){
        return new ResponseEntity<Author>(IAuthorService.createAuthor(authorModel, ERole.ROLE_AUTHOR), HttpStatus.CREATED);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest tokenRequest){
        return new ResponseEntity<>(new JwtResponse(authService.refreshToken(tokenRequest)), HttpStatus.OK);
    }







}
