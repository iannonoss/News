package com.example.news.service;

import com.example.news.dto.ERole;
import com.example.news.dto.UserModel;
import com.example.news.entity.Reader;
import com.example.news.entity.Role;
import com.example.news.exception.ItemAlreadyExistsException;
import com.example.news.exception.ResourceNotFoundException;
import com.example.news.repository.RoleRepository;
import com.example.news.repository.ReaderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ReaderServiceImpl implements ReaderService {

    @Autowired
    ReaderRepository readerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private  AuthorService authorService;

    @Autowired
    private UserService userService;

    public Reader createUser(UserModel userModel, String setRole) {
        if (userService.existUserByEmail(userModel.getEmail())) {
            throw new ItemAlreadyExistsException
                    ("User is already register with email: "
                            + userModel.getEmail());
        }
        Reader newReader = new Reader();
        BeanUtils.copyProperties(userModel, newReader);
        newReader.setPassword(passwordEncoder.encode(newReader.getPassword()));

        Set<Role> roles = new HashSet<>();

        switch (setRole) {
            case "mod":
                Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(modRole);

                break;
            default:
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
        }
        newReader.setRoles(roles);
        return readerRepository.save(newReader);
    }

    @Override
    public Reader readUser() {
        Long userId = userService.getLoggedInUser().getId();
        return readerRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for the id:"+userId));
    }

    @Override
    public Reader updateUser(UserModel user) {
        Reader existingReader = readUser();
        existingReader.setName(user.getName() != null ? user.getName() : existingReader.getName());
        existingReader.setSurname(user.getSurname() != null ? user.getSurname() : existingReader.getSurname());
        existingReader.setEmail(user.getEmail() != null ? user.getEmail() : existingReader.getEmail());
        existingReader.setPassword(user.getPassword() != null ? passwordEncoder.encode(user.getPassword()): existingReader.getPassword());
        existingReader.setBirthDate(user.getBirthDate() != null ? user.getBirthDate() : existingReader.getBirthDate());

        return readerRepository.save(existingReader);
    }

    public void delete() {
        Reader existingReader = readUser();
        readerRepository.delete(existingReader);
    }


}
