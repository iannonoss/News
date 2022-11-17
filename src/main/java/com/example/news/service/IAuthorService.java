package com.example.news.service;

import com.example.news.dto.AuthorModel;
import com.example.news.entity.Author;

public interface IAuthorService {

    Author createAuthor(AuthorModel authorModel);

    Author readAuthor(Long authorId);

    Author getLoggedInAuthor();

    Author getAuthorFromEmail(String email);

    void delete(Author author);



}
