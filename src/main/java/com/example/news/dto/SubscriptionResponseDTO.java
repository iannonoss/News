package com.example.news.dto;

import com.example.news.entity.Subscription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionResponseDTO {

    private String email;

    private Long id;

    private Boolean state;

    private Date start_date;

    private Date end_date;

}
