package com.democom.news.dto;

import com.democom.news.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
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

    public ReaderProfileResponseDTO(Long id, String name, String surname, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }
}
