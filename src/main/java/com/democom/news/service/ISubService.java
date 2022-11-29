package com.democom.news.service;

import com.democom.news.dto.SaveSubRequestDTO;
import com.democom.news.dto.SubResponseDTO;
import com.democom.news.dto.SubsDTO;
import com.democom.news.exception.NotFoundEx;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;

public interface ISubService {

    SubResponseDTO createSub(SaveSubRequestDTO subModel) throws NotFoundEx, ParseException;


    Page<SubsDTO> getSubsAuthor(Pageable page, Long id);

    Page<SubsDTO> getSubsReader(Pageable page, Long id);

}
