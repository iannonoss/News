package com.democom.news.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ResetPasswordResponse {

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
}
