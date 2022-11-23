package com.example.news.repository;

import com.example.news.dto.NewsResponseDto;
import com.example.news.entity.Author;
import com.example.news.entity.News;
import com.example.news.entity.Reader;
import com.example.news.util.Queries;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.nio.channels.NonWritableChannelException;
import java.util.Date;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @Query(value = Queries.getSubbedNewsByCategory)
    Page<NewsResponseDto> findByCategory(Reader reader,String category, Pageable page);

    Page<News> findByAuthor(Author author, Pageable pageable);

    @Query(value = "SELECT new com.example.news.dto.NewsResponseDto(n.title ,n.category ,n.description ,n.date ,n.author.name) FROM News n ")
    Page<NewsResponseDto> findAllNews(Pageable page);


    //Page<News> findByDateBetween( Date startDate, Date endDate, Pageable page);

    @Query(value = Queries.getSubbedNews)
    Page<NewsResponseDto> findNewsSubbed(Reader reader, Pageable page);
}
