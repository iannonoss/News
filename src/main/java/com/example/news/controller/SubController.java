package com.example.news.controller;

import com.example.news.dto.SaveSubRequest;
import com.example.news.dto.SubResponseDTO;
import com.example.news.entity.Subscription;
import com.example.news.exception.NotFoundEx;
import com.example.news.service.ISubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
public class SubController extends BaseController{


    @Autowired
    private ISubService subService;

    @PostMapping("/submit-subscription/{id}")
    public ResponseEntity<SubResponseDTO>saveSub(@PathVariable(value = "id") Long id) throws NotFoundEx, ParseException {
        SaveSubRequest subModel = new SaveSubRequest(id, getUserLoggedId().getId());
        return new ResponseEntity<SubResponseDTO>(subService.createSub(subModel), HttpStatus.CREATED);
    }
}
