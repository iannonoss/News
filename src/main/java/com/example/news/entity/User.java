package com.example.news.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
    @Entity
    @Table(name = "tbl_user")
    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.TABLE)
        private Long id;


        private String name;

        private String surname;

        @Column(unique = true)
        private String email;

        @JsonIgnore
        private String password;

        private LocalDate birthDate;

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(	name = "user_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
        private Set<Role> roles = new HashSet<>();



    }


