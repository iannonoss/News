package com.example.news.controller;

import com.example.news.config.IAuthenticationFacade;
import com.example.news.entity.Author;
import com.example.news.entity.Reader;
import com.example.news.service.IAuthorService;
import com.example.news.service.IReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    @Autowired
    IAuthenticationFacade authenticationFacade;

    @Autowired
    private IReaderService IReaderService;
    @Autowired
    private IAuthorService IAuthorService;

    protected Reader getUserLoggedId(){
        Authentication authentication = authenticationFacade.getAuthentication();
        String email = authentication.getName();
        return IReaderService.getUserFromEmail(email);
    }

    protected Author getAuthorLoggedId(){
        Authentication authentication = authenticationFacade.getAuthentication();
        String email = authentication.getName();
        return IAuthorService.getAuthorFromEmail(email);
    }
}
