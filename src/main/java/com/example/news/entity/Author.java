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
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idA;


    private String name;

    private String surname;

    @Column(unique = true)
    private String email;

    private String password;

    private LocalDate birthDate;

    private String biography;

    private String category;

    private BigDecimal subscription_price;


    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    private Set<Subscription> subscriptions;


}
