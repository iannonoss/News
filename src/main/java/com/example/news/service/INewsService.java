package com.example.news.service;

import com.example.news.dto.NewsResponseDto;
import com.example.news.entity.News;
import com.example.news.entity.Reader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface INewsService {

    Page<NewsResponseDto> getAllNews(Pageable page);

    News getNewsById(Long id);

    NewsResponseDto saveAllNewsDetails(News news);

    News updateNewsDetails(News news, Long id);

    //News updatePartialNewsDetails(News news, Long id);

    News updateNews(News news, Long id);

    void deleteNewsById(Long id);

    Page<NewsResponseDto> readByCategory(Reader reader,String category, Pageable page);

    //News readNews();

    List<News> readNewsByAuthor(Long id, Pageable page);

     Page<NewsResponseDto> getSubNews(Reader reader, Pageable page);

    List<News> readByDate(Date startDate, Date endDate, Pageable page);


}
