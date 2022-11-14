package com.example.news.service;

import com.example.news.dto.UserModel;
import com.example.news.entity.Reader;

public interface ReaderService {

    Reader createUser(UserModel userModel, String role);

    Reader readUser();

     Reader updateUser(UserModel user);

    void delete();
}
