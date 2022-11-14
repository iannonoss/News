package com.example.news.service;

import com.example.news.entity.User;

public interface UserService {
    Boolean existUserByEmail(String email);

    User getLoggedInUser();

    User getUserFromEmail(String email);
}
