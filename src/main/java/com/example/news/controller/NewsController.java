package com.example.news.controller;

import com.example.news.entity.News;
import com.example.news.exception.ResourceNotFoundException;
import com.example.news.repository.NewsRepository;
import com.example.news.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class NewsController {
    @Autowired
    private INewsService newsService;

    @Autowired
    private NewsRepository newsRepository;

    @GetMapping("/list_of_news")
    public List<News> getAllNews(Pageable page){
        return  newsService.getAllNews(page).toList();
    }

    @GetMapping("/news/{id}")
    public News getNewsById(@PathVariable Long id){
          return   newsService.getNewsById(id);
    }

    @GetMapping("/author/newsList/{id}")
    public ResponseEntity<List<News>> readNews(@PathVariable Long id, Pageable pageable){
        return  new ResponseEntity<>(newsService.readNewsByAuthor(id, pageable), HttpStatus.OK);
    }


    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/news")
    public News saveNews(@Valid @RequestBody News news){
        return newsService.saveAllNewsDetails(news);
    }


    @PutMapping("/news/update/{id}")
    public ResponseEntity<News> updateNewsPartially(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody News news) throws ResourceNotFoundException {
        return ResponseEntity.ok(newsService.updateNews(news, id));
    }


    @PatchMapping("/news/updateAll/{id}")
    public News updateAllNewsDetails(@RequestBody News news, @PathVariable Long id){
        return newsService.updateNewsDetails(news, id);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/news/delete")
    public void deleteNewsById(@RequestParam Long id){
        newsService.deleteNewsById(id);
    }


    @GetMapping("/news/category")
    public List<News> getAllNewsByCategory(@RequestParam String category, Pageable page ){
        return newsService.readByCategory(category, page);
    }

   /* @GetMapping("/news/category")
    public List<News> getAllNewsByCategory(@RequestParam String category, Pageable page ){
        return newsService.readByCategory(category, page);
    }

    @GetMapping("/news/author")
    public List<News> getNewsByAuthor(@RequestParam String author, Pageable page){
        return newsService.readByAuthor(author, page);
    }

    @GetMapping("/news/date")
    public List<News> getExpenseByDateBetween(@RequestParam (required = false) Date starDate,
                                              @RequestParam (required = false) Date endDate,
                                              Pageable page){

        return newsService.readByDate(starDate, endDate, page);
    }*/



}
