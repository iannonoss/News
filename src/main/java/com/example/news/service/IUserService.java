package com.example.news.service;

import com.example.news.entity.User;

public interface IUserService {
    Boolean existUserByEmail(String email);

    User getLoggedInUser();

    User getUserFromEmail(String email);
}
