package com.example.news.repository;

import com.example.news.entity.Reader;
import com.example.news.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {

    Optional<Reader> findByEmail(String email);

    Boolean existsByEmail(String email);


}
