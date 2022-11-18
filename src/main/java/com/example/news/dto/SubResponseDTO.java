package com.example.news.dto;

import com.example.news.entity.Author;
import com.example.news.entity.Reader;
import lombok.Data;

import java.util.Date;

@Data
public class SubResponseDTO {

    private Long id;

    private String readerId;

    private String authorId;

    private Boolean state_subscription;

    private Date start_date;

    private Date end_Date;
}
