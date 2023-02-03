package com.democom.news.service;

import com.democom.news.entity.User;

import java.util.Optional;

public interface IUserService {
    Boolean existUserByEmail(String email);

    User getLoggedInUser();

    User getUserFromEmail(String email);

    void saveUser(User user);

    Optional<User> getUserFromPasswordToken(String code);

}
