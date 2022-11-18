package com.example.news.service;

import com.example.news.dto.AuthorModel;
import com.example.news.entity.Author;
import com.example.news.exception.NotFoundEx;

public interface IAuthorService {

    Author createAuthor(AuthorModel authorModel);

    Author readAuthor(Long authorId);

    Author getLoggedInAuthor();

    Author getAuthorFromEmail(String email);

    void delete(Author author);

    boolean existByIdAuthor(Long id) throws NotFoundEx;

     Author findAuthorById(Long id);



}
