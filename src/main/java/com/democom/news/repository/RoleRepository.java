package com.democom.news.repository;

import com.democom.news.dto.enums.ERole;
import com.democom.news.entity.Role;
import com.democom.news.util.Queries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);

    @Query(value = Queries.getRoleByName)
    Role findRoleByName(ERole role);
}
