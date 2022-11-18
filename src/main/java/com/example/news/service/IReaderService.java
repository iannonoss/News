package com.example.news.service;

import com.example.news.dto.ERole;
import com.example.news.dto.UserModel;
import com.example.news.entity.Reader;

public interface IReaderService {

    Reader createReader(UserModel userModel, ERole role);

    Reader readReader();

     Reader updateReader( UserModel user);

    void delete(Reader reader);

    Reader getUserFromEmail(String email);

     Reader getLoggedInReader();
}
