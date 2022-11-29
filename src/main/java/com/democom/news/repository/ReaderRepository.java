package com.democom.news.repository;

import com.democom.news.dto.ReaderProfileResponseDTO;
import com.democom.news.entity.Reader;
import com.democom.news.dto.NewsResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {

    Optional<Reader> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query(value = "SELECT new com.democom.news.dto.ReaderProfileResponseDTO(r.id, r.name, r.surname, r.email) FROM Reader r ")
    Page<ReaderProfileResponseDTO> findAllReader(Pageable page);


}
