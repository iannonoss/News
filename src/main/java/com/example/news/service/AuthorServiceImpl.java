package com.example.news.service;

import com.example.news.dto.AuthorModel;
import com.example.news.entity.Author;
import com.example.news.entity.User;
import com.example.news.exception.ItemAlreadyExistsException;
import com.example.news.exception.ResourceNotFoundException;
import com.example.news.repository.AuthorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService{

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public Author createAuthor(AuthorModel authorModel) {
        if(authorRepository.existsByEmail(authorModel.getEmail())) {
            throw new ItemAlreadyExistsException("Author is already registered with email:" + authorModel.getEmail());
        }
        Author newAuthor = new Author();
        BeanUtils.copyProperties(authorModel, newAuthor);
        newAuthor.setPassword(passwordEncoder.encode(newAuthor.getPassword()));
        return authorRepository.save(newAuthor);
    }

    @Override
    public Author readAuthor() {
        Long authorId = getLoggedInAuthor().getIdA();
        return authorRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Author not found for the id:"+authorId));
    }

    @Override
    public Author getLoggedInAuthor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return authorRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Author not found for the email;"+email));
    }
}
