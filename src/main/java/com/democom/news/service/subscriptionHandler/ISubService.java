package com.democom.news.service.subscriptionHandler;

import com.democom.news.dto.SaveSubRequestDTO;
import com.democom.news.dto.SubResponseDTO;
import com.democom.news.dto.SubsDTO;
import com.democom.news.entity.Reader;
import com.democom.news.exception.NotFoundEx;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;

public interface ISubService {

    SubResponseDTO createSub(SaveSubRequestDTO subModel, Reader reader) throws NotFoundEx, ParseException;


    Page<SubsDTO> getSubsAuthor(Pageable page, Long id);

    Page<SubsDTO> getSubsReader(Pageable page, Long id);

}
