package com.example.news.service;

import com.example.news.dto.SaveSubRequest;
import com.example.news.dto.SubResponseDTO;
import com.example.news.entity.Author;
import com.example.news.entity.Reader;
import com.example.news.entity.Subscription;
import com.example.news.exception.NotFoundEx;

import java.text.ParseException;
import java.util.List;

public interface ISubService {

    SubResponseDTO createSub(SaveSubRequest subModel) throws NotFoundEx, ParseException;


}
