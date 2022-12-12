package com.democom.news.service;

import com.democom.news.dto.NewsResponseDto;
import com.democom.news.entity.Author;
import com.democom.news.entity.News;
import com.democom.news.entity.Reader;
import com.democom.news.exception.ResourceNotFoundException;
import com.democom.news.service.newsHandler.INewsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(scripts = "init-db.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class NewsServiceImplTest {

    @Autowired
    private INewsService newsService;


    @Test
    void getSubNews() {
        final int sizeSubbedNews = 4;
        List<NewsResponseDto> newsResponseDtoList = newsService.getSubNews(takePresentUser(), Pageable.unpaged()).toList();
        assertEquals(sizeSubbedNews, newsResponseDtoList.size());
    }

    @Test
    void readByCategory() {
        final String category = "category2";
        List<NewsResponseDto> newsResponseDtoList = newsService.readByCategory(takePresentUser(),category,Pageable.unpaged()).toList();
        assertTrue(newsResponseDtoList.stream()
                .allMatch(newsResponseDto -> newsResponseDto.getCategory().equals(category)));
    }

    @Test
    void readByDate() {
        Date date1 = new Date();
        Calendar c = Calendar.getInstance();
        c.set(2021, Calendar.DECEMBER, 30);
        date1 = c.getTime();
        c.add(Calendar.YEAR, 1);
        Date date2 = c.getTime();
        newsService.readByDate(date1, date2, Pageable.unpaged());
        assertEquals(6, newsService.readByDate(date1, date2, Pageable.unpaged()).size());
    }

    @Test
    void saveAllNewsDetails() {

    }

    //TODO: update logic's patch & put
   /* @Test
    void updateNewsDetails() {

    }

    @Test
    void updateNews() {
    }*/

    @Test
    void deleteNewsById() {
        newsService.deleteNewsById(1L);
        assertThrowsExactly(ResourceNotFoundException.class,
                () -> newsService.deleteNewsById(1L));
    }

    @Test
    void readNewsByAuthor() {
        List<News> news = newsService.readNewsByAuthor(takePresentAuthor(), Pageable.unpaged());

        assertAll(
                () -> assertEquals(10,news.get(1).getAuthor().getId()),
                () -> assertEquals(3,news.size())
        );
        }

    @Test
    void getAllNews() {
        assertEquals(6,newsService.getAllNews(Pageable.unpaged()).getSize());
    }

    @Test
    void getNewsById() {
    }



    private Reader takePresentUser() {
        Reader reader = new Reader();
        reader.setId(2L);
        reader.setName("Giovanni");
        reader.setSurname("Bianchi");
        reader.setEmail("g.bianchi@gmail.com");
        reader.setPassword("$2a$10$T1YkmQfIfNXHrJTdsj7UYO1QAdvFvWb6ryvEhJ.X50n0MZfLIfvOG");
        reader.setBirthDate(LocalDate.parse("1980-10-18"));
        reader.setBalance(BigDecimal.valueOf(180).setScale(2));
        reader.setSubscriptions(new ArrayList<>());
        return reader;
    }


    private Author takePresentAuthor() {
        Author author = new Author();
        author.setId(10L);
        author.setName("Michele");
        author.setSurname("Vitantonio");
        author.setEmail("m.vitantonio@gmail.com");
        author.setPassword("$2a$10$T1YkmQfIfsXHrJTds7UYO1QAdvFvWb6ryvEhJ.X50n0MZfLIfvOG");
        author.setBirthDate(LocalDate.parse("1980-10-18"));
        author.setCategory("Sport");
        author.setAlias("Michelino");
        author.setSubscription_price(BigDecimal.valueOf(20).setScale(2));
        author.setBio("Bio di Michele");
        author.setSubscriptions(new ArrayList<>());
        return author;
    }



}