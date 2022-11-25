package com.example.news.dto;

import com.example.news.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class AuthorSortPriceResponse {

    private Long id;

    private String name;

    private String surname;

    private String alias;

    private String bio;

    private String category;

    private BigDecimal subscription_price;

    public AuthorSortPriceResponse(Long id, String name, String surname, String alias, String bio, String category, BigDecimal subscription_price) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.alias = alias;
        this.bio = bio;
        this.category = category;
        this.subscription_price = subscription_price;
    }

    public static AuthorSortPriceResponse build(Author author){
        return new AuthorSortPriceResponse(author.getId(), author.getName(), author.getSurname(), author.getAlias(), author.getBio(), author.getCategory(), author.getSubscription_price());
    }


}
