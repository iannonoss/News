package com.example.news.service;

import com.example.news.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface INewsService {

    Page<News> getAllNews(Pageable page);

    News getNewsById(Long id);

    News saveNewsDetails(News news);

    News updateNewsDetails(News news, Long id);

    void deleteNewsById(Long id);

    List<News> readByCategory(String category, Pageable page);

    List<News> readByAuthor(String author, Pageable page);

    List<News> readByDate(Date startDate, Date endDate, Pageable page);


}
