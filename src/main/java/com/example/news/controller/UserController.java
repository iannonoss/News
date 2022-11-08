package com.example.news.controller;

import com.example.news.dto.UserModel;
import com.example.news.entity.User;
import com.example.news.exception.ResourceNotFoundException;
import com.example.news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/profile")
    public ResponseEntity<User> readUser(){
        return  new ResponseEntity<User>(userService.readUser(), HttpStatus.OK);
    }

    @PutMapping("/profile")
    public  ResponseEntity<User> updateUser(@RequestBody UserModel user){
        return new ResponseEntity<User>(userService.updateUser(user), HttpStatus.OK);

    }

    @DeleteMapping("/deactivate")
    public ResponseEntity<HttpStatus> deleteUser() throws ResourceNotFoundException {
        userService.delete();
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }
}
