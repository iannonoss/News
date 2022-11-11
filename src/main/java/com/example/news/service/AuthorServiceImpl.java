package com.example.news.service;

import com.example.news.dto.AuthorModel;
import com.example.news.dto.ERole;
import com.example.news.dto.UserModel;
import com.example.news.entity.Author;
import com.example.news.entity.Role;
import com.example.news.entity.User;
import com.example.news.exception.ItemAlreadyExistsException;
import com.example.news.exception.ResourceNotFoundException;
import com.example.news.repository.AuthorRepository;
import com.example.news.repository.RoleRepository;
import com.example.news.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthorServiceImpl implements AuthorService{

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Author createAuthor(AuthorModel authorModel) {
        if(authorRepository.existsByEmail(authorModel.getEmail())) {
            throw new ItemAlreadyExistsException("Author is already registered with email:" + authorModel.getEmail());
        }
        Author newAuthor = new Author();
        BeanUtils.copyProperties(authorModel, newAuthor);
        newAuthor.setPassword(passwordEncoder.encode(newAuthor.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role modRole = roleRepository.findByName(ERole.ROLE_AUTHOR)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(modRole);
        newAuthor.setRoles(roles);
        return authorRepository.save(newAuthor);
    }

    @Override
    public Author readAuthor() {
        Long authorId = getLoggedInAuthor().getId();
        return authorRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Author not found for the id:"+authorId));
    }

    @Override
    public Author getLoggedInAuthor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return authorRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Author not found for the email;"+email));
    }

    @Override
    public Author searchAuthorByEmail(String email) {
        return authorRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Author with the email:" + email + "not found."));
    }

}
