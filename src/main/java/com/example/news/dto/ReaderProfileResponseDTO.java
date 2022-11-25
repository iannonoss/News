package com.example.news.dto;

import com.example.news.entity.Role;
import com.example.news.entity.Subscription;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
public class ReaderProfileResponseDTO {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private LocalDate birthDate;

    private BigDecimal balance;

    private Set<Role> roles = new HashSet<>();

    private Collection<SubscriptionResponseDTO> subscriptions;

}
