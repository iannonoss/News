package com.democom.news.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_author")
public class Author extends User {


    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String bio;

    private String category;

    private String alias;

    private BigDecimal subscription_price;


    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private Collection<Subscription> subscriptions;

}
