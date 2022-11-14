package com.example.news.controller;

import com.example.news.dto.UserModel;
import com.example.news.entity.Reader;
import com.example.news.exception.ResourceNotFoundException;
import com.example.news.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReaderController {

    @Autowired
    private ReaderService readerService;


    @GetMapping("/profile")
    public ResponseEntity<Reader> readUser(){
        return  new ResponseEntity<Reader>(readerService.readUser(), HttpStatus.OK);
    }

    @PutMapping("/profile")
    public  ResponseEntity<Reader> updateUser(@RequestBody UserModel user){
        return new ResponseEntity<Reader>(readerService.updateUser(user), HttpStatus.OK);

    }

    @DeleteMapping("/deactivate")
    public ResponseEntity<HttpStatus> deleteUser() throws ResourceNotFoundException {
        readerService.delete();
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }
}
