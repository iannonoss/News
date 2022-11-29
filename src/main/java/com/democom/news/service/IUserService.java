package com.democom.news.service;

import com.democom.news.entity.User;

public interface IUserService {
    Boolean existUserByEmail(String email);

    User getLoggedInUser();

    User getUserFromEmail(String email);
}
