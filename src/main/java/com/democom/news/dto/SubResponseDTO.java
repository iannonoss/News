package com.democom.news.dto;

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
