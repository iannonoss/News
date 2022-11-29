package com.democom.news.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
public class UserModel {

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
    private BigDecimal balance;

    @NotNull
    private LocalDate birthDate;

    private Set<String> role;

}
