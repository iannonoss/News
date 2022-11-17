package com.example.news.controller;

import com.example.news.dto.*;
import com.example.news.entity.Author;
import com.example.news.entity.JwtResponse;
import com.example.news.entity.Reader;
import com.example.news.service.IAuthorService;
import com.example.news.service.IAuthService;
import com.example.news.service.IReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    private IReaderService userService;

    @Autowired
    private IAuthorService IAuthorService;

    @Autowired
    private IAuthService authService;



    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody AuthModel authModel) throws Exception {
        return new ResponseEntity<>(authService.login(authModel), HttpStatus.OK);
    }

    @PostMapping("/register-user")
    public ResponseEntity<Reader> saveUser(@Valid @RequestBody UserModel userModel){
        return new ResponseEntity<Reader>(userService.createReader(userModel, ERole.ROLE_USER ), HttpStatus.CREATED);
    }
    @PostMapping("/register-mod")
    public ResponseEntity<Reader> saveMod(@Valid @RequestBody UserModel userModel){
        return new ResponseEntity<Reader>(userService.createReader(userModel, ERole.ROLE_MODERATOR ), HttpStatus.CREATED);
    }

    @PostMapping("/register-author")
    public ResponseEntity<Author> saveAuthor(@Valid @RequestBody AuthorModel authorModel){
        return new ResponseEntity<Author>(IAuthorService.createAuthor(authorModel), HttpStatus.CREATED);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest tokenRequest){
        return new ResponseEntity<>(new JwtResponse(authService.refreshToken(tokenRequest)), HttpStatus.OK);
    }








}
