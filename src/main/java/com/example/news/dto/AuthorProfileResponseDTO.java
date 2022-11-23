package com.example.news.dto;

import com.example.news.entity.Subscription;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Data
public class AuthorProfileResponseDTO {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private LocalDate birthDate;

    private String category;

    private BigDecimal subscription_price;


    private Collection<SubscriptionResponseDTO> subscriptions;

}
