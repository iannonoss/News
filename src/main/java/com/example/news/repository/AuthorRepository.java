package com.example.news.repository;

import com.example.news.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findAuthorByEmail(String email);

    Boolean existsByEmail(String email);

    boolean existsById(Long id);

    Optional<Author> findByEmail(String email);
    

}
