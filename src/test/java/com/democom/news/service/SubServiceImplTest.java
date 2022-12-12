package com.democom.news.service;

import com.democom.news.dto.SaveSubRequestDTO;
import com.democom.news.dto.SubResponseDTO;
import com.democom.news.dto.SubsDTO;
import com.democom.news.entity.Author;
import com.democom.news.entity.Reader;
import com.democom.news.exception.NotFoundEx;
import com.democom.news.service.subscriptionHandler.ISubService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(scripts = "init-db.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class SubServiceImplTest {

    @Autowired
    ISubService subService;

    @Test
    @DisplayName("Sub creation")
    void createSub() throws NotFoundEx, ParseException {
      Reader reader = takePresentUserNotSubbed();
      Author author = takePresentAuthor();
      SaveSubRequestDTO saveSubRequestDTO =  new SaveSubRequestDTO(author.getId(),reader.getId());
      SubResponseDTO subResponseDTO = subService.createSub(saveSubRequestDTO,reader);
      assertAll(
              () -> assertEquals(Long.parseLong(subResponseDTO.getAuthorId()), author.getId()),
              () -> assertEquals(Long.parseLong(subResponseDTO.getReaderId()), reader.getId())
      );
    }

    @Test
    @DisplayName("Sub already one")
    void createSubAlreadyDone(){
        Reader reader = takePresentUser();
        Author author = takePresentAuthorAlreadySubbed();
        SaveSubRequestDTO saveSubRequestDTO =  new SaveSubRequestDTO(author.getId(),reader.getId());
        assertThrowsExactly(Error.class, () -> subService.createSub(saveSubRequestDTO, reader));
    }

    @Test
    void getSubsAuthor() {
        final int sizeSubs = 3;
        List<SubsDTO> subsDTOS = subService.getSubsAuthor(Pageable.unpaged(), takePresentAuthor().getId()).toList();
        assertEquals(sizeSubs, subsDTOS.size());
    }

    @Test
    void getSubsReader() {
        final int sizeSubs = 3;
        List<SubsDTO> subsDTOS = subService.getSubsReader(Pageable.unpaged(), takePresentUser().getId()).toList();
        assertEquals(sizeSubs, subsDTOS.size());
    }

    private Author takePresentAuthorAlreadySubbed() {
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

    private Author takePresentAuthor() {
        Author author = new Author();
        author.setId(11L);
        author.setName("Davide");
        author.setSurname("Russo");
        author.setEmail("d.russo@gmail.com");
        author.setPassword("$2a$10$T1YkmQfIfsXHrJTds7UYO1QAdvFvWb6ryvEhJ.X50n0MZfLIfvOG");
        author.setBirthDate(LocalDate.parse("1980-10-18"));
        author.setCategory("Sport");
        author.setAlias("davide");
        author.setSubscription_price(BigDecimal.valueOf(10).setScale(2));
        author.setBio("Bio di Davide");
        author.setSubscriptions(new ArrayList<>());
        return author;


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


    private Reader takePresentUserNotSubbed() {
        Reader reader = new Reader();
        reader.setId(3L);
        reader.setName("Marco");
        reader.setSurname("Di Donato");
        reader.setEmail("m.didonato@gmail.com");
        reader.setPassword("$2a$10$ZR.s.y9LVfFEUHpRU6sVkOYJcmHXncfEIlBU5uAEa6Vwr/XHLfcV6");
        reader.setBirthDate(LocalDate.parse("1998-05-29"));
        reader.setBalance(BigDecimal.valueOf(180).setScale(2));
        reader.setSubscriptions(new ArrayList<>());
        return reader;
    }
}