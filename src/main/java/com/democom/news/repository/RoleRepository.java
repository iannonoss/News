package com.democom.news.repository;

import com.democom.news.dto.ERole;
import com.democom.news.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);

    @Query(value = "SELECT new com.democom.news.entity.Role(r.name) FROM Role r WHERE r.name =?1")
    Role findRoleByName(ERole role);
}
