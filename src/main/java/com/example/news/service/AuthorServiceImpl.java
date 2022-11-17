package com.example.news.service;

import com.example.news.dto.AuthorModel;
import com.example.news.dto.ERole;
import com.example.news.entity.Author;
import com.example.news.entity.Role;
import com.example.news.exception.ItemAlreadyExistsException;
import com.example.news.exception.ResourceNotFoundException;
import com.example.news.repository.AuthorRepository;
import com.example.news.repository.RoleRepository;
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
public class AuthorServiceImpl implements IAuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private IUserService IUserService;

    @Override
    public Author createAuthor(AuthorModel authorModel) {
        if(IUserService.existUserByEmail(authorModel.getEmail())) {
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
    public Author readAuthor(Long authorId) {
        return authorRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Author not found for the id:"+authorId));
    }

    @Override
    public Author getLoggedInAuthor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return authorRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Author not found for the email;"+email));
    }


    @Override
    public Author getAuthorFromEmail(String email) {
        return authorRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User not found with email: " + email));
    }

    @Override
    public void delete(Author author) {
        authorRepository.delete(author);
    }


}
