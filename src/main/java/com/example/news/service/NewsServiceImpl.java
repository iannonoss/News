package com.example.news.service;

import com.example.news.entity.Author;
import com.example.news.entity.News;
import com.example.news.exception.ResourceNotFoundException;
import com.example.news.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NewsServiceImpl implements INewsService {
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private AuthorService authorService;

    @Autowired
    private UserService userService;


    @Override
    public Page<News> getAllNews(Pageable page) {
        return newsRepository.findAll(page);
    }

    @Override
    public News getNewsById(Long id) {
        Optional<News> news = newsRepository.findById(id);
        if (news.isPresent()) {
            return news.get();
        }
        throw new ResourceNotFoundException("Resources is not found for the id ->"+id);
    }

    @Override
    public News saveNewsDetails(News news) {
        news.setAuthor((Author) userService.getLoggedInUser());
        return newsRepository.save(news);
    }

    @Override
    public News updateNewsDetails(News news, Long id) {
            News existingNews = getNewsById(id);
            existingNews.setTitle(news.getTitle() != null ?  news.getTitle() : existingNews.getTitle());
            existingNews.setCategory(news.getCategory() != null ?  news.getCategory() : existingNews.getCategory());
            existingNews.setDescription(news.getDescription() != null ?  news.getDescription() : existingNews.getDescription());
            existingNews.setAuthor(news.getAuthor() != null ?  news.getAuthor() : existingNews.getAuthor());
            existingNews.setDate(news.getDate() != null ?  news.getDate() : existingNews.getDate());
            return newsRepository.save(existingNews);

    }

    @Override
    public void deleteNewsById(Long id) {
        News news = getNewsById(id);
        newsRepository.delete(news);
    }

    @Override
    public List<News> readByCategory(String category, Pageable page) {
        return newsRepository.findByCategory(category, page).toList();
    }

    @Override
    public List<News> readByAuthor(String author, Pageable page) {
        return newsRepository.findByAuthor(author, page).toList();
    }

    @Override
    public List<News> readByDate(Date startDate, Date endDate, Pageable page) {
        if(startDate == null ){
            startDate = new Date(0);
        }
        if (endDate == null){
            endDate = new Date(System.currentTimeMillis());
        }
        return newsRepository.findByDateBetween(startDate, endDate, page).toList();
    }

}
