package com.example.news.controller;

import com.example.news.dto.AuthorProfileResponseDTO;
import com.example.news.dto.AuthorSortPriceResponse;
import com.example.news.entity.Author;
import com.example.news.exception.ResourceNotFoundException;
import com.example.news.service.IAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthorController extends BaseController {

    @Autowired
    private IAuthorService IAuthorService;

   /* @GetMapping("/profile-author")
    public ResponseEntity<Author> readAuthor(){
        return  new ResponseEntity<Author>(IAuthorService.getLoggedInAuthor(), HttpStatus.OK);
    }*/

    @GetMapping("/profile-author")
    public AuthorProfileResponseDTO readProfile(){
        Author user = getAuthorLoggedId();
        return IAuthorService.readAuthor(user);
    }

    @DeleteMapping("/deactivateAuthor")
    public ResponseEntity<HttpStatus> deleteAuthor() throws ResourceNotFoundException {
        IAuthorService.delete(getAuthorLoggedId());
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/author/subPrice")
    public List<AuthorSortPriceResponse> readAuthorByPrice(Pageable page){
        return IAuthorService.sortByPrice(page).toList();
    }




}
