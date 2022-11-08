package com.example.news.service;

import com.example.news.dto.UserModel;
import com.example.news.entity.User;
import com.example.news.exception.ItemAlreadyExistsException;
import com.example.news.exception.ResourceNotFoundException;
import com.example.news.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User createUser(UserModel userModel) {
        if (userRepository.existsByEmail(userModel.getEmail())) {
            throw new ItemAlreadyExistsException
                    ("User is already register with email: "
                            + userModel.getEmail());
        }
        User newUser = new User();
        BeanUtils.copyProperties(userModel, newUser);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found for the email;"+email));
    }

    @Override
    public User readUser() {
        Long userId = getLoggedInUser().getId();
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for the id:"+userId));
    }

    @Override
    public User updateUser(UserModel user) {
        User existingUser = readUser();
        existingUser.setName(user.getName() != null ? user.getName() : existingUser.getName());
        existingUser.setSurname(user.getSurname() != null ? user.getSurname() : existingUser.getSurname());
        existingUser.setEmail(user.getEmail() != null ? user.getEmail() : existingUser.getEmail());
        existingUser.setPassword(user.getPassword() != null ? passwordEncoder.encode(user.getPassword()): existingUser.getPassword());
        existingUser.setBirthDate(user.getBirthDate() != null ? user.getBirthDate() : existingUser.getBirthDate());

        return userRepository.save(existingUser);
    }

    public void delete() {
        User existingUser = readUser();
        userRepository.delete(existingUser);
    }


}
