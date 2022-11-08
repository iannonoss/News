package com.example.news.repository;

import com.example.news.entity.Author;
import com.example.news.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findAuthorByEmail(String email);

    Boolean existsByEmail(String email);

    Optional<Author> findByEmail(String email);

}
