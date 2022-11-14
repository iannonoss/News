package com.example.news.security;

import com.example.news.entity.Reader;
import com.example.news.entity.User;
import com.example.news.repository.AuthorRepository;
import com.example.news.repository.ReaderRepository;
import com.example.news.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorRepository authorRepository;


   /* @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> existingUser = userRepository.findUserByEmail(email);
        if(existingUser.isPresent()) {
            return CustomUserDetails.build(existingUser.get());
        }else {
            Author existingAuthor = authorRepository.findAuthorByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found for the email:"+email));
            return new org.springframework.security.core.userdetails.User(existingAuthor.getEmail(), existingAuthor.getPassword(), new ArrayList<>());
        }
    }*/

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User existingUser = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with the email: " + email + " not found."));
        return CustomUserDetails.build(existingUser);
    }






}
