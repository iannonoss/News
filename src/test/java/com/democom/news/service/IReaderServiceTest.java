package com.democom.news.service;

import com.democom.news.dto.enums.ERole;
import com.democom.news.dto.FundsDTO;
import com.democom.news.dto.UserModel;
import com.democom.news.entity.Reader;
import com.democom.news.exception.ResourceNotFoundException;
import com.democom.news.service.readerHandler.IReaderService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
class IReaderServiceTest {

    @Autowired
    IReaderService readerService;

    @Test
    void createReader() {
        Reader notPresentReader = takeNotPresentUser();
        UserModel readerDTO = new UserModel();
        BeanUtils.copyProperties(notPresentReader, readerDTO);
        Reader savedReader = readerService.createReader(readerDTO, ERole.ROLE_USER);

        assertAll(
                () -> assertEquals(readerDTO.getName(), savedReader.getName()),
                () -> assertEquals(readerDTO.getEmail(), savedReader.getEmail()),
                () -> assertEquals(readerDTO.getBirthDate(), savedReader.getBirthDate()),
                () -> assertEquals(ERole.ROLE_USER, savedReader.getRoles().iterator().next().getName())
        );
        readerService.delete(notPresentReader);
    }

/*
    @Test
    void readReader() {
    }*/

    @Test
    void readProfile() {
        Reader reader = takePresentUser();

        assertAll(
                () -> assertEquals(reader.getName(), readerService.readProfile(reader).getName()),
                () -> assertEquals(reader.getEmail(), readerService.readProfile(reader).getEmail()),
                () -> assertEquals(reader.getBirthDate(), readerService.readProfile(reader).getBirthDate()),
                () -> assertEquals(reader.getBalance(), readerService.readProfile(reader).getBalance())
        );
    }

    @Test
    @DisplayName("Update reader credentials")
    void updateReader() {

        Reader reader = new Reader();
        reader.setName("Giovanni");
        reader.setBirthDate(LocalDate.parse("2000-10-28"));
        reader.setBalance(BigDecimal.valueOf(15));

        Reader existingReader = takePresentUser();
        readerService.updateReader(reader, existingReader);

        assertAll(
                () -> assertEquals(existingReader.getName(), reader.getName()),
                () -> assertEquals(existingReader.getBirthDate(), reader.getBirthDate()),
                () -> assertEquals(existingReader.getBalance(), reader.getBalance()),
                () -> assertNotEquals(existingReader.getEmail(), reader.getEmail()),
                () -> assertNotEquals(existingReader.getPassword(), reader.getPassword())
        );
    }

    @Test
    @DisplayName("Delete Reader")
    void delete() {
        readerService.delete(readerService.getUserFromEmail("m.iannone@gmail.com"));

        assertThrowsExactly(ResourceNotFoundException.class,
                () -> readerService.delete(readerService.getUserFromEmail("a.rossi@gmail.com")));

    }

    @Test
    @DisplayName("Get existing reader rom email")
    void getUserFromEmail() {
        Reader existingReader = takePresentUser();

        Reader readerFromRepo = readerService.getUserFromEmail(existingReader.getEmail());

        assertAll(
                () -> assertEquals(existingReader.getEmail(), readerFromRepo.getEmail()),
                () -> assertEquals(existingReader.getId(), readerFromRepo.getId()),
                () -> assertEquals(existingReader.getBalance(), readerFromRepo.getBalance())
        );
    }

   /* @Test
    void getLoggedInReader() {
    }*/

    @Test
    @DisplayName("Get all reader")
    void getAllReader() {
        assertEquals(2, readerService.getAllReader(Pageable.unpaged()).getSize());
    }

    @Test
    void addFunds() {
        Reader reader = takePresentUser();
        float f = 20;
        FundsDTO fundsDTO = new FundsDTO();
       fundsDTO.setMoney(f);
        readerService.addFunds(reader,fundsDTO);
        assertThat(BigDecimal.valueOf(200),  Matchers.comparesEqualTo(reader.getBalance()));

    }
    private Reader takeNotPresentUser() {
        Reader reader = new Reader();
        reader.setId(6L);
        reader.setName("Alberto");
        reader.setEmail("a.vinello@gmail.com");
        reader.setPassword("12345");
        reader.setBirthDate(LocalDate.parse("1995-06-24"));
        reader.setBalance(BigDecimal.valueOf(250.00).setScale(2));
        reader.setSubscriptions(new ArrayList<>());
        return reader;
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
}



