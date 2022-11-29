package com.democom.news.service;

import com.democom.news.dto.ERole;
import com.democom.news.dto.ReaderProfileResponseDTO;
import com.democom.news.dto.UserModel;
import com.democom.news.entity.Reader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface IReaderService {

    Reader createReader(UserModel userModel, ERole role);

    Reader readReader();

    ReaderProfileResponseDTO readProfile(Reader reader);

     Reader updateReader( UserModel user);

    void delete(Reader reader);

    Reader getUserFromEmail(String email);

     Reader getLoggedInReader();

     Page<ReaderProfileResponseDTO> getAllReader(Pageable page);

     Reader addFunds(Reader reader, BigDecimal funds);
}
