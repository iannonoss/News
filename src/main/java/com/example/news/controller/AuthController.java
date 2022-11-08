package com.example.news.controller;

import com.example.news.dto.AuthModel;
import com.example.news.dto.AuthorModel;
import com.example.news.dto.UserModel;
import com.example.news.entity.Author;
import com.example.news.entity.JwtResponse;
import com.example.news.entity.User;
import com.example.news.security.AuthService;
import com.example.news.service.AuthorService;
import com.example.news.service.UserService;
import com.example.news.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthService userDetailsService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody AuthModel authModel) throws Exception {
        authenticate(authModel.getEmail(), authModel.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authModel.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails); //generate the jwt token
        return  new ResponseEntity<JwtResponse>(new JwtResponse(token), HttpStatus.OK);
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e){
            throw new Exception("User disabled");
        } catch (BadCredentialsException e){
            throw new Exception("Bad credentials");
        }
    }

    @PostMapping("/register-user")
    public ResponseEntity<User> saveUser(@Valid @RequestBody UserModel userModel){
        return new ResponseEntity<User>(userService.createUser(userModel), HttpStatus.CREATED);
    }

    @PostMapping("/register-author")
    public ResponseEntity<Author> saveAuthor(@Valid @RequestBody AuthorModel authorModel){
        return new ResponseEntity<Author>(authorService.createAuthor(authorModel), HttpStatus.CREATED);
    }




}
