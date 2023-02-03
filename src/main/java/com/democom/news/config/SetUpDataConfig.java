package com.democom.news.config;

import com.democom.news.dto.enums.ERole;
import com.democom.news.entity.Role;
import com.democom.news.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;


@Component
public class SetUpDataConfig implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;
        createRoleIfNotFound(ERole.ROLE_USER);
        createRoleIfNotFound(ERole.ROLE_MODERATOR);
        createRoleIfNotFound(ERole.ROLE_AUTHOR);
        alreadySetup = true;
    }

    @Transactional //dynamically creates a proxy that implements the same interface(s) as the class you're annotating.
    Role createRoleIfNotFound(ERole setRole) {
        Role role = roleRepository.findRoleByName(setRole);

        if (role == null) {
            role = new Role();
            role.setName(setRole);
            roleRepository.save(role);
        }
        return role;
    }


}

