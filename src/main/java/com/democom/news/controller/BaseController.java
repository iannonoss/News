package com.democom.news.controller;

import com.democom.news.entity.Author;
import com.democom.news.entity.Reader;
import com.democom.news.config.IAuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    @Autowired
    IAuthenticationFacade authenticationFacade;

    @Autowired
    private com.democom.news.service.readerHandler.IReaderService IReaderService;
    @Autowired
    private com.democom.news.service.auhtorHandler.IAuthorService IAuthorService;

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
