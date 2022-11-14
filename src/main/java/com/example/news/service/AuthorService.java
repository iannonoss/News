package com.example.news.service;

import com.example.news.dto.AuthorModel;
import com.example.news.entity.Author;

public interface AuthorService {

    Author createAuthor(AuthorModel authorModel);

    Author readAuthor();

    Author getLoggedInAuthor();

    Author searchAuthorByEmail(String email);
}
