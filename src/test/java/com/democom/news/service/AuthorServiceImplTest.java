package com.democom.news.service;

import com.democom.news.dto.AuthorModel;
import com.democom.news.dto.AuthorSortPriceResponseDTO;
import com.democom.news.dto.enums.ERole;
import com.democom.news.entity.Author;
import com.democom.news.exception.ResourceNotFoundException;
import com.democom.news.service.auhtorHandler.IAuthorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(scripts = "init-db.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class AuthorServiceImplTest {

    @Autowired
    private IAuthorService authorService;

    @Test
    @DisplayName("Author creation")
    void createAuthor() {

        Author notPresentAuthor = takeNotPresentAuthor();
        AuthorModel authorDTO = new AuthorModel();
        BeanUtils.copyProperties(notPresentAuthor, authorDTO);
        Author savedAuthor = authorService.createAuthor(authorDTO, ERole.ROLE_AUTHOR);

        assertAll(
                () -> assertEquals(authorDTO.getName(), savedAuthor.getName()),
                () -> assertEquals(authorDTO.getEmail(), savedAuthor.getEmail()),
                () -> assertEquals(authorDTO.getBirthDate(), savedAuthor.getBirthDate()),
                () -> assertEquals(ERole.ROLE_AUTHOR, savedAuthor.getRoles().iterator().next().getName())
        );
        authorService.delete(notPresentAuthor);

    }

    @Test
    void sortByPrice() {
        Page<AuthorSortPriceResponseDTO> list = authorService.sortByPrice(Pageable.unpaged());
        ArrayList<BigDecimal> pricesList = new ArrayList<BigDecimal>();
        BigDecimal sorted[] = {BigDecimal.valueOf(10).setScale(2), BigDecimal.valueOf(20).setScale(2), BigDecimal.valueOf(40).setScale(2)};
        for (AuthorSortPriceResponseDTO authors: list){
                pricesList.add(authors.getSubscription_price());
        }


        assertAll(
                () -> assertEquals(sorted[0],pricesList.get(0)),
                () -> assertEquals(sorted[1],pricesList.get(1)),
                () -> assertEquals(sorted[2],pricesList.get(2))
        );
    }

    @Test
    void getAuthorFromEmail() {
        Author existingSAuthor = takePresentAuthor();
        Author authorFromRepo = authorService.getAuthorFromEmail(existingSAuthor.getEmail());
        assertEquals(existingSAuthor, authorFromRepo);
    }

    @Test
    void delete() {
        authorService.delete(authorService.getAuthorFromEmail("m.vitantonio@gmail.com"));
        assertThrowsExactly(ResourceNotFoundException.class,
                () -> authorService.delete(authorService.getAuthorFromEmail("a.rossi@gmail.com")));
    }

    @Test
    void existByIdAuthor() {
        Author author = takePresentAuthor();
        assertEquals(2, author.getId());
    }


    private Author takeNotPresentAuthor() {
        Author author = new Author();
        author.setId(6L);
        author.setName("Alberto");
        author.setSurname("Vinello");
        author.setEmail("a.vinello@gmail.com");
        author.setPassword("12345");
        author.setBirthDate(LocalDate.parse("1995-06-24"));
        author.setCategory("Caccia");
        author.setAlias("Albi");
        author.setSubscription_price(BigDecimal.valueOf(20).setScale(2));
        author.setBio("AAAAAAAAAAAAAAAAAAAAAA");
        author.setSubscriptions(new ArrayList<>());
        return author;
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