package com.democom.news.controller;

import com.democom.news.dto.SaveSubRequestDTO;
import com.democom.news.dto.SubResponseDTO;
import com.democom.news.dto.SubsDTO;
import com.democom.news.exception.NotFoundEx;
import com.democom.news.service.ISubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
public class SubController extends BaseController{


    @Autowired
    private ISubService subService;

    @PostMapping("/submit-subscription/{id}")
    public ResponseEntity<SubResponseDTO>saveSub(@PathVariable(value = "id") Long id) throws NotFoundEx, ParseException {
        SaveSubRequestDTO subModel = new SaveSubRequestDTO(id, getUserLoggedId().getId());
        return new ResponseEntity<SubResponseDTO>(subService.createSub(subModel), HttpStatus.CREATED);
    }

    @GetMapping("/author/listOfAuthorSub/{id}")
    public List<SubsDTO> readAllAuthorSubs(@PathVariable(value = "id") Long id, Pageable page){
        return subService.getSubsAuthor(page, id).toList();
    }

    @GetMapping("/author/listOfReaderSub/{id}")
    public List<SubsDTO> readAllReaderSubs(@PathVariable(value = "id") Long id, Pageable page){
        return subService.getSubsReader(page, id).toList();
    }
}
