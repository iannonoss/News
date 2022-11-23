package com.example.news.controller;

import com.example.news.dto.ReaderProfileResponseDTO;
import com.example.news.dto.UserModel;
import com.example.news.entity.Reader;
import com.example.news.exception.ResourceNotFoundException;
import com.example.news.service.IReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReaderController extends BaseController{

    @Autowired
    private IReaderService readerService;


    @GetMapping("/profile")
    public ResponseEntity<ReaderProfileResponseDTO> readProfile(){
        Reader reader = getUserLoggedId();
        return  new ResponseEntity<ReaderProfileResponseDTO>(readerService.readProfile(reader), HttpStatus.OK);
    }

    @PutMapping("/profile")
    public  ResponseEntity<Reader> update(@RequestBody UserModel user){
        return new ResponseEntity<Reader>(readerService.updateReader(user), HttpStatus.OK);

    }

    @DeleteMapping("/deactivate")
    public ResponseEntity<HttpStatus> delete() throws ResourceNotFoundException {
        readerService.delete(getUserLoggedId());
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }
}
