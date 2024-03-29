package com.democom.news.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@ToString
@EqualsAndHashCode(exclude = "invoice")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reader_id")
    private Reader reader;


    @ManyToOne(fetch = FetchType.EAGER)
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
