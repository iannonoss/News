package com.example.news.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

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


    @OneToMany(mappedBy = "author")
    private Collection<Subscription> subscriptions;

}
