package com.example.news.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveSubRequest {
    Long idAuthor;
    Long idReader;
}
