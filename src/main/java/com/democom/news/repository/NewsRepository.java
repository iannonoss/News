package com.democom.news.repository;

import com.democom.news.entity.Author;
import com.democom.news.entity.Reader;
import com.democom.news.dto.NewsResponseDto;
import com.democom.news.entity.News;
import com.democom.news.util.Queries;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @Query(value = Queries.getSubbedNewsByCategory)
    Page<NewsResponseDto> findByCategory(Reader reader, String category, Pageable page);

    Page<News> findByAuthor(Author author, Pageable pageable);

    @Query(value = "SELECT new com.democom.news.dto.NewsResponseDto(n.title ,n.category ,n.description ,n.date ,n.author.name) FROM News n ")
    Page<NewsResponseDto> findAllNews(Pageable page);


    @Query(value = Queries.getNewsByDataBetween)
    Page<News> findByDateBetween( Date startDate, Date endDate, Pageable page);

    @Query(value = Queries.getSubbedNews)
    Page<NewsResponseDto> findNewsSubbed(Reader reader, Pageable page);
}
