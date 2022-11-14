package com.example.news.repository;

import com.example.news.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {


    Page<News> findByCategory(String category, Pageable page);

    //Page<News> findByAuthor(String author, Pageable pageable);

    Page<News> findByDateBetween( Date startDate, Date endDate, Pageable page);

}
