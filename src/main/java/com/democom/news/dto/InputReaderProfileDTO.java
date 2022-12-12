package com.democom.news.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
public class InputReaderProfileDTO {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private BigDecimal balance;
    private  LocalDate birthDate;
}
