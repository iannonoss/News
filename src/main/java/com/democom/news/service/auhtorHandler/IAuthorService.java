package com.democom.news.service.auhtorHandler;

import com.democom.news.dto.AuthorSortPriceResponseDTO;
import com.democom.news.dto.AuthorModel;
import com.democom.news.dto.AuthorProfileResponseDTO;
import com.democom.news.dto.enums.ERole;
import com.democom.news.entity.Author;
import com.democom.news.exception.NotFoundEx;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAuthorService {

    Author createAuthor(AuthorModel authorModel, ERole role);

    AuthorProfileResponseDTO readAuthor(Author author);

    Author getLoggedInAuthor();

    Author getAuthorFromEmail(String email);

    void delete(Author author);

    boolean existByIdAuthor(Long id) throws NotFoundEx;

     Author findAuthorById(Long id);

     Page<AuthorSortPriceResponseDTO> sortByPrice(Pageable page);






}
