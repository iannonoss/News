package com.democom.news.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

@Data
public class AuthorProfileResponseDTO {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private String bio;

    private String alias;

    private LocalDate birthDate;

    private String category;

    private BigDecimal subscription_price;


    private Collection<SubscriptionResponseDTO> subscriptions;

}
