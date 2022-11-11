package com.example.news.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AuthorModel {

    @NotBlank(message = "Please enter name")
    private String name;

    @NotBlank(message = "Please enter surname")
    private String surname;

    @NotNull(message = "Please enter email")
    @Email(message = "Please enter valid email")
    private String email;

    @NotNull(message = "Please enter password")
    @Size(min = 5, message = "Password should be at least 5 characters")
    private String password;

    @NotNull
    private LocalDate birthDate;

    @NotBlank
    private String biography;

    @NotBlank(message = "Category should not be null")
    private String category;

    @NotNull(message = "Author's subscription price amount should be not null")
    private BigDecimal subscription_price;
}
