package com.example.news.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class JwtResponse {

    private final String jwtToken;

}
