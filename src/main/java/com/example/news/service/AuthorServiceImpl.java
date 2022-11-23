package com.example.news.service;

import com.example.news.dto.AuthorModel;
import com.example.news.dto.AuthorProfileResponseDTO;
import com.example.news.dto.ERole;
import com.example.news.dto.SubscriptionResponseDTO;
import com.example.news.entity.Author;
import com.example.news.entity.Role;
import com.example.news.entity.Subscription;
import com.example.news.exception.ItemAlreadyExistsException;
import com.example.news.exception.NotFoundEx;
import com.example.news.exception.ResourceNotFoundException;
import com.example.news.repository.AuthorRepository;
import com.example.news.repository.RoleRepository;
import com.example.news.repository.SubRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
@Autowired
  SubRepository subRepository;

    @Override
    public Author createAuthor(AuthorModel authorModel) {
        if (IUserService.existUserByEmail(authorModel.getEmail())) {
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
    public AuthorProfileResponseDTO readAuthor(Author author) {
        return buildResponseDTO(author);
    }

    private AuthorProfileResponseDTO buildResponseDTO(Author author) {
        AuthorProfileResponseDTO authorProfileResponseDTO = new AuthorProfileResponseDTO();

        authorProfileResponseDTO.setId(author.getId());
        authorProfileResponseDTO.setName(author.getName());
        authorProfileResponseDTO.setSurname(author.getSurname());
        authorProfileResponseDTO.setEmail(author.getEmail());
        authorProfileResponseDTO.setBirthDate(author.getBirthDate());
        authorProfileResponseDTO.setCategory(author.getCategory());
        authorProfileResponseDTO.setSubscription_price(author.getSubscription_price());
        authorProfileResponseDTO.setSubscriptions(new ArrayList<>());
        List<Subscription> subscriptions = subRepository.findAllByAuthorId(authorProfileResponseDTO.getId());
        for (Subscription subscription : subscriptions) {
            SubscriptionResponseDTO subscriptionResponseDTO = new SubscriptionResponseDTO();
            if (subscription.getStateSubscription()){
            subscriptionResponseDTO.setEmail(subscription.getReader().getEmail());
            subscriptionResponseDTO.setId(subscription.getReader().getId());
            subscriptionResponseDTO.setState(subscription.getStateSubscription());
            subscriptionResponseDTO.setStart_date(subscription.getStart_date());
            subscriptionResponseDTO.setEnd_date(subscription.getEnd_date());

            authorProfileResponseDTO.getSubscriptions().add(subscriptionResponseDTO);
        }}

        return authorProfileResponseDTO;
    }

    @Override
    public Author getLoggedInAuthor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return authorRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Author not found for the email;" + email));
    }


    public Author findAuthorById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found with id:" + id));
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

    @Override
    public boolean existByIdAuthor(Long id) throws NotFoundEx {
        if (authorRepository.existsById(id)) {
            return true;
        } else throw new NotFoundEx("Author not found with id:" + id);
    }

}
