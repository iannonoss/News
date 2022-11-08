package com.example.news.service;

import com.example.news.dto.AuthorModel;
import com.example.news.entity.Author;
import com.example.news.entity.User;

public interface AuthorService {

    Author createAuthor(AuthorModel authorModel);

    Author readAuthor();

    Author getLoggedInAuthor();

}
