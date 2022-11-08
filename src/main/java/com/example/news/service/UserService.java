package com.example.news.service;

import com.example.news.dto.UserModel;
import com.example.news.entity.User;

public interface UserService {

    User createUser(UserModel userModel);

    User getLoggedInUser();

    User readUser();

     User updateUser(UserModel user);

    void delete();
}
