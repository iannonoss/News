package com.example.news.controller;

import com.example.news.entity.Author;
import com.example.news.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/profile-author")
    public ResponseEntity<Author> readAuthor(){
        return  new ResponseEntity<Author>(authorService.readAuthor(), HttpStatus.OK);
    }

}
