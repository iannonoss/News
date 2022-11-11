package com.example.news.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_author")
public class Author extends User{


    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String category;

    private BigDecimal subscription_price;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    private Set<Subscription> subscriptions;



}
