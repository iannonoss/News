package com.democom.news.service;

import com.democom.news.entity.User;
import com.democom.news.exception.ResourceNotFoundException;
import com.democom.news.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public Boolean existUserByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found for the email;"+email));
    }

    @Override
    public User getUserFromEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User not found with email: " + email));
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }


    @Override
    public Optional<User> getUserFromPasswordToken(String code) {
        return userRepository.findByResetPasswordCode(code);
    }

}
