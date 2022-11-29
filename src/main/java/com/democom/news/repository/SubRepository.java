package com.democom.news.repository;

import com.democom.news.entity.Author;
import com.democom.news.entity.Reader;
import com.democom.news.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubRepository extends JpaRepository<Subscription,Long> {

    Boolean existsByAuthorAndReaderAndStateSubscription(Author author, Reader reader, Boolean value);

    List<Subscription> findAllByAuthorId(Long id);

    List<Subscription> findAllByReaderId(Long id);


   List<Subscription> findSubscriptionByAuthorId(Long id);

   List<Subscription> findSubscriptionByReaderId(Long id);

}
