package com.example.news.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    private Boolean stateSubscription;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date start_date;

    private Date end_date;



    @PrePersist
    private void onCreate() {
        start_date = new Date();
    }



}
