package com.example.news.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reader_id")
    private Reader reader;


    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @NotNull
    private Boolean state_subscription;

    @NotNull
    private Date start_date;

    @NotNull
    private Date end_date;





}
