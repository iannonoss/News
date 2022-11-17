package com.example.news.repository;

import com.example.news.dto.ERole;
import com.example.news.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.hibernate.loader.Loader.SELECT;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);

    @Query(value = "SELECT new com.example.news.entity.Role(r.name) FROM Role r WHERE r.name =?1")
    Role findRoleByName(ERole role);
}
