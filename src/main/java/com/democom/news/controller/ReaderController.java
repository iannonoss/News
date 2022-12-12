package com.democom.news.controller;

import com.democom.news.dto.FundsDTO;
import com.democom.news.entity.Reader;
import com.democom.news.dto.ReaderProfileResponseDTO;
import com.democom.news.exception.ResourceNotFoundException;
import com.democom.news.service.readerHandler.IReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

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
    public  ResponseEntity<Reader> update(@RequestBody Reader reader){
        Reader oldReader = getUserLoggedId();
        return new ResponseEntity<Reader>(readerService.updateReader(reader, oldReader), HttpStatus.OK);
    }

    @DeleteMapping("/deactivate")
    public ResponseEntity<HttpStatus> delete() throws ResourceNotFoundException {
        readerService.delete(getUserLoggedId());
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("admin/list_of_users")
    public List<ReaderProfileResponseDTO> getAllNews(Pageable page) {
        return  readerService.getAllReader(page).toList();
    }


    //TODO: fare patch di ogni cosa

    @PatchMapping("reader/addFounds")
    public  ResponseEntity<ReaderProfileResponseDTO> addFundsOfReader(@RequestBody FundsDTO funds) {
        return new ResponseEntity<ReaderProfileResponseDTO>(readerService.addFunds(getUserLoggedId(), funds), HttpStatus.OK);
    }

}
