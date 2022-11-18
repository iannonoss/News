package com.example.news.repository;

import com.example.news.entity.Author;
import com.example.news.entity.Reader;
import com.example.news.entity.Subscription;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubRepository extends JpaRepository<Subscription,Long> {

    Boolean existsByAuthorAndReaderAndStateSubscription(Author author, Reader reader, Boolean value);



    Optional<Subscription> findByAuthorAndReaderAndStateSubscription(Author author, Reader reader, Boolean value);



}
