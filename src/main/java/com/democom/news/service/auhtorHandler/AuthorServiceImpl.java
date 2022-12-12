package com.democom.news.service.auhtorHandler;


import com.democom.news.dto.*;
import com.democom.news.dto.enums.ERole;
import com.democom.news.entity.Author;
import com.democom.news.entity.Role;
import com.democom.news.entity.Subscription;
import com.democom.news.exception.ItemAlreadyExistsException;
import com.democom.news.exception.NotFoundEx;
import com.democom.news.exception.ResourceNotFoundException;
import com.democom.news.repository.AuthorRepository;
import com.democom.news.repository.RoleRepository;
import com.democom.news.repository.SubRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements IAuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private com.democom.news.service.IUserService IUserService;
    @Autowired
    private SubRepository subRepository;

    @Override
    public Author createAuthor(AuthorModel authorModel, ERole setRole) {
        if (IUserService.existUserByEmail(authorModel.getEmail())) {
            throw new ItemAlreadyExistsException("Author is already registered with email:" + authorModel.getEmail());
        }
        Author newAuthor = new Author();
        BeanUtils.copyProperties(authorModel, newAuthor);
        newAuthor.setPassword(passwordEncoder.encode(newAuthor.getPassword()));
        this.setAuthorRole(setRole, newAuthor);

        return authorRepository.save(newAuthor);
    }

    private void setAuthorRole(ERole role, Author author){
        Role targetRole = roleRepository.findByName(role).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> roles = new HashSet<>();
        roles.add(targetRole);
        author.setRoles(roles);
    }

    @Override
    public AuthorProfileResponseDTO readAuthor(Author author) {
        return buildResponseDTO(author);
    }


    //TODO
    private AuthorProfileResponseDTO buildResponseDTO(Author author) {
        AuthorProfileResponseDTO authorProfileResponseDTO = new AuthorProfileResponseDTO();

        authorProfileResponseDTO.setId(author.getId());
        authorProfileResponseDTO.setName(author.getName());
        authorProfileResponseDTO.setSurname(author.getSurname());
        authorProfileResponseDTO.setEmail(author.getEmail());
        authorProfileResponseDTO.setAlias(author.getAlias());
        authorProfileResponseDTO.setBio(author.getBio());
        authorProfileResponseDTO.setBirthDate(author.getBirthDate());
        authorProfileResponseDTO.setCategory(author.getCategory());
        authorProfileResponseDTO.setSubscription_price(author.getSubscription_price());
        authorProfileResponseDTO.setSubscriptions(new ArrayList<>());
        List<Subscription> subscriptions = subRepository.findAllByAuthorId(authorProfileResponseDTO.getId());
        for (Subscription subscription : subscriptions) {
            //TODO
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
//TODO
    @Override
    public Page<AuthorSortPriceResponseDTO> sortByPrice(Pageable pageable) {
        List<Author> list;
        list = authorRepository.findAll();
        list.sort(Comparator.comparing(Author::getSubscription_price));
        List<AuthorSortPriceResponseDTO> authorSortPriceResponseList = list.stream().map(a -> new AuthorSortPriceResponseDTO(a.getId(),a.getName(),a.getSurname(),a.getAlias(),a.getBio(),a.getCategory(),a.getSubscription_price())).collect(Collectors.toList());
        return new PageImpl<>(authorSortPriceResponseList, pageable,authorSortPriceResponseList.size());
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
