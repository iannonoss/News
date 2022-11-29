package com.democom.news.service;

import com.democom.news.entity.Author;
import com.democom.news.entity.Reader;
import com.democom.news.dto.NewsResponseDto;
import com.democom.news.entity.News;
import com.democom.news.exception.CategoryNotFoundException;
import com.democom.news.exception.MismatchedIdException;
import com.democom.news.exception.ResourceNotFoundException;
import com.democom.news.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NewsServiceImpl implements INewsService {
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private IAuthorService IAuthorService;


    //READER OPERATION
    public Page<NewsResponseDto> getSubNews(Reader reader, Pageable page) {
           return newsRepository.findNewsSubbed(reader, page);
    }

    @Override
    public Page<NewsResponseDto> readByCategory(Reader reader, String category, Pageable page) {
        Page<NewsResponseDto> filterByCategory = newsRepository.findByCategory(reader,category, page);
        if (filterByCategory.hasContent()) {
            return filterByCategory;
        }throw new CategoryNotFoundException("Category not found");

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


/*
    private NewsResponseDto buildResponseDTO(News news){
        NewsResponseDto newsResponseDto = new NewsResponseDto();

        newsResponseDto.setTitle(news.getTitle());
        newsResponseDto.setCategory(news.getCategory());
        newsResponseDto.setDescription(news.getDescription());
        newsResponseDto.setDate(news.getDate());
        newsResponseDto.setAuthor(news.getAuthor().getName());

        return newsResponseDto;

    }
*/



    //AUTHOR OPERATION OR MODERATOR
    @Override
    public NewsResponseDto saveAllNewsDetails(News news) {
        news.setAuthor(IAuthorService.getLoggedInAuthor());
        newsRepository.save(news);
        return buildDTO(news);

    }

    private NewsResponseDto buildDTO(News news) {
        NewsResponseDto newsResponseDto = new NewsResponseDto();
        newsResponseDto.setTitle(news.getTitle());
        newsResponseDto.setCategory(news.getCategory());
        newsResponseDto.setDescription(news.getDescription());
        newsResponseDto.setAuthor(news.getAuthor().getAlias());
        newsResponseDto.setDate(news.getDate());
        return newsResponseDto;
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

    private boolean checkModifier(Long id){
        return Objects.equals(id, IAuthorService.getLoggedInAuthor().getId());
    }

    @Override
    public void deleteNewsById(Long id) {
        News news = getNewsById(id);
        newsRepository.delete(news);
    }

    //ADMIN/MODERATOR  OPERATION



    @Override
    public List<News> readNewsByAuthor(Long id, Pageable page) {
        Author author = IAuthorService.getLoggedInAuthor();
        return newsRepository.findByAuthor(author, page).toList();
    }

    @Override
    public Page<NewsResponseDto> getAllNews(Pageable page) {
        return newsRepository.findAllNews(page);
    }



    @Override
    public News getNewsById(Long id) {
        Optional<News> news = newsRepository.findById(id);
        if (news.isPresent()) {
            return news.get();
        }
        throw new ResourceNotFoundException("Resources is not found for the id ->"+id);
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
*/




}
