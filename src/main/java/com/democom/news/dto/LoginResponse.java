package com.democom.news.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String jwt;
    private String refreshToken;
}
