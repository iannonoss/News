package com.example.news.service;

import com.example.news.dto.AuthorModel;
import com.example.news.dto.AuthorProfileResponseDTO;
import com.example.news.dto.AuthorSortPriceResponse;
import com.example.news.entity.Author;
import com.example.news.exception.NotFoundEx;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAuthorService {

    Author createAuthor(AuthorModel authorModel);

    AuthorProfileResponseDTO readAuthor(Author author);

    Author getLoggedInAuthor();

    Author getAuthorFromEmail(String email);

    void delete(Author author);

    boolean existByIdAuthor(Long id) throws NotFoundEx;

     Author findAuthorById(Long id);


     Page<AuthorSortPriceResponse> sortByPrice(Pageable page);


}
