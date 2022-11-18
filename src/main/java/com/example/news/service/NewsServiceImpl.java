package com.example.news.service;

import com.example.news.entity.Author;
import com.example.news.entity.News;
import com.example.news.exception.MismatchedIdException;
import com.example.news.exception.ResourceNotFoundException;
import com.example.news.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class NewsServiceImpl implements INewsService {
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private IAuthorService IAuthorService;

    @Autowired
    private IReaderService IReaderService;


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
    public News saveAllNewsDetails(News news) {
        news.setAuthor(IAuthorService.getLoggedInAuthor());
        return newsRepository.save(news);
    }


    @Override
    public News updateNewsDetails(News news, Long id) {
        if(checkModifier(getNewsById(id).getAuthor().getId())) {
            News existingNews = getNewsById(id);
            existingNews.setTitle(news.getTitle() != null ? news.getTitle() : existingNews.getTitle());
            existingNews.setCategory(news.getCategory() != null ? news.getCategory() : existingNews.getCategory());
            existingNews.setDescription(news.getDescription() != null ? news.getDescription() : existingNews.getDescription());
            return newsRepository.save(existingNews);
        } else throw new MismatchedIdException("Mismatched Id, Author not authorized ");

    }

    @Override
    public News updateNews(News news, Long id){
        if(checkModifier(getNewsById(id).getAuthor().getId())) {
        News newNews = newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on :: "+ id));
        newNews.setTitle(news.getTitle());
        newNews.setCategory(newNews.getCategory());
        newNews.setDescription(newNews.getDescription());
        return  newsRepository.save(newNews);
        } else throw new MismatchedIdException("Mismatched Id, Author not authorized ");

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
    public List<News> readNewsByAuthor(Long id, Pageable page) {
        Author author = IAuthorService.getLoggedInAuthor();
        return newsRepository.findByAuthor(author, page).toList();
    }


    private boolean checkModifier(Long id){
        return Objects.equals(id, IAuthorService.getLoggedInAuthor().getId());
    }








/*
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
*/

}
