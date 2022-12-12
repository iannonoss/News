package com.democom.news.service.readerHandler;

import com.democom.news.dto.enums.ERole;
import com.democom.news.dto.FundsDTO;
import com.democom.news.dto.ReaderProfileResponseDTO;
import com.democom.news.dto.UserModel;
import com.democom.news.entity.Reader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IReaderService {

    Reader createReader(UserModel userModel, ERole role);

    Reader readReader();

    ReaderProfileResponseDTO readProfile(Reader reader);

     Reader updateReader(Reader reader, Reader oldReader);

    void delete(Reader reader);

    Reader getUserFromEmail(String email);

     Reader getLoggedInReader();

     Page<ReaderProfileResponseDTO> getAllReader(Pageable page);

     ReaderProfileResponseDTO addFunds(Reader reader, FundsDTO funds);
}
