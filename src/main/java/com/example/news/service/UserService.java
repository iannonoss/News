package com.example.news.service;

import com.example.news.dto.AuthModel;
import com.example.news.dto.AuthorModel;
import com.example.news.dto.UserModel;
import com.example.news.entity.User;

public interface UserService {

    User createUser(UserModel userModel, String role);

    User getLoggedInUser();

    User getUserFromEmail(String email);

    User readUser();

     User updateUser(UserModel user);

    void delete();
}
