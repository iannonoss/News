package com.democom.news.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsResponseDto {


    private String title;

    private String category;

    private String description;

    private Date publicationDate;

    private String author;

}
