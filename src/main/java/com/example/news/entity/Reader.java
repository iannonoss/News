package com.example.news.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_reader")
public class Reader extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @ToString.Exclude
    @OneToMany(mappedBy = "reader")
    private Set<Subscription> subscriptions;
}
