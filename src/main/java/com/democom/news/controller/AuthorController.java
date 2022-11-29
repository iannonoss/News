package com.democom.news.controller;

import com.democom.news.dto.AuthorSortPriceResponseDTO;
import com.democom.news.entity.Author;
import com.democom.news.dto.AuthorProfileResponseDTO;
import com.democom.news.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
    private com.democom.news.service.IAuthorService authorService;

    @GetMapping("/profile-author")
    public AuthorProfileResponseDTO readProfile(){
        Author user = getAuthorLoggedId();
        return authorService.readAuthor(user);
    }

    @DeleteMapping("/deactivateAuthor")
    public ResponseEntity<HttpStatus> deleteAuthor() throws ResourceNotFoundException {
        authorService.delete(getAuthorLoggedId());
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/author/subPrice")
    public List<AuthorSortPriceResponseDTO> readAuthorByPrice(Pageable page){
        return authorService.sortByPrice(page).toList();
    }






}
