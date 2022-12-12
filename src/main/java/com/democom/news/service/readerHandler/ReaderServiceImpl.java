package com.democom.news.service.readerHandler;

import com.democom.news.dto.*;
import com.democom.news.dto.enums.ERole;
import com.democom.news.entity.Reader;
import com.democom.news.entity.Role;
import com.democom.news.entity.Subscription;
import com.democom.news.exception.ItemAlreadyExistsException;
import com.democom.news.exception.ResourceNotFoundException;
import com.democom.news.repository.RoleRepository;
import com.democom.news.repository.ReaderRepository;
import com.democom.news.repository.SubRepository;
import com.democom.news.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ReaderServiceImpl implements IReaderService {

    @Autowired
    ReaderRepository readerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private IUserService userService;

    @Autowired
    private SubRepository subRepository;

    public Reader createReader(UserModel userModel, ERole setRole) {
        if (userService.existUserByEmail(userModel.getEmail())) {
            throw new ItemAlreadyExistsException
                    ("User is already register with email: "
                            + userModel.getEmail());
        }
        Reader newReader = new Reader();
        BeanUtils.copyProperties(userModel, newReader);
        newReader.setPassword(passwordEncoder.encode(newReader.getPassword()));
        this.setReaderRole(setRole,newReader);
        return readerRepository.save(newReader);
    }

    private void setReaderRole(ERole role, Reader reader){
        Role targetRole = roleRepository.findByName(role).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> roles = new HashSet<>();
        roles.add(targetRole);
        reader.setRoles(roles);
    }



    @Override
    public ReaderProfileResponseDTO readProfile(Reader reader) {
        return buildResponseDTO(reader);
    }


    private ReaderProfileResponseDTO buildResponseDTO(Reader reader) {
        ReaderProfileResponseDTO readerProfileResponseDTO = new ReaderProfileResponseDTO();

        readerProfileResponseDTO.setId(reader.getId());
        readerProfileResponseDTO.setName(reader.getName());
        readerProfileResponseDTO.setSurname(reader.getSurname());
        readerProfileResponseDTO.setEmail(reader.getEmail());
        readerProfileResponseDTO.setBirthDate(reader.getBirthDate());
        readerProfileResponseDTO.setBalance(reader.getBalance());
        readerProfileResponseDTO.setRoles(reader.getRoles());
        readerProfileResponseDTO.setSubscriptions(new ArrayList<>());
        List<Subscription> subscriptions = subRepository.findAllByReaderId(readerProfileResponseDTO.getId());

        for (Subscription subscription : subscriptions) {
            SubscriptionResponseDTO subscriptionResponseDTO = new SubscriptionResponseDTO();
            if (subscription.getStateSubscription()){
                subscriptionResponseDTO.setEmail(subscription.getAuthor().getEmail());
                subscriptionResponseDTO.setId(subscription.getAuthor().getId());
                subscriptionResponseDTO.setState(subscription.getStateSubscription());
                subscriptionResponseDTO.setStart_date(subscription.getStart_date());
                subscriptionResponseDTO.setEnd_date(subscription.getEnd_date());


                readerProfileResponseDTO.getSubscriptions().add(subscriptionResponseDTO);
            }}

        return readerProfileResponseDTO;
    }


    @Override
    public Reader readReader() {
        Long userId = userService.getLoggedInUser().getId();
        return readerRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for the id:"+userId));
    }

    @Override
    public Reader updateReader(Reader reader, Reader oldReader) {
        oldReader.setName(reader.getName() != null ? reader.getName() : oldReader.getName());
        oldReader.setSurname(reader.getSurname() != null ? reader.getSurname() : oldReader.getSurname());
        oldReader.setEmail(reader.getEmail() != null ? reader.getEmail() : oldReader.getEmail());
        oldReader.setPassword(reader.getPassword() != null ? passwordEncoder.encode(reader.getPassword()): oldReader.getPassword());
        oldReader.setBirthDate(reader.getBirthDate() != null ? reader.getBirthDate() : oldReader.getBirthDate());
        oldReader.setBalance(reader.getBalance() != null ? reader.getBalance() : oldReader.getBalance());

        return readerRepository.save(oldReader);
    }

    public void delete(Reader reader) {
        readerRepository.delete(reader);
    }

    @Override
    public Reader getUserFromEmail(String email) {
        return readerRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User not found with email: " + email));
    }


    public Reader getLoggedInReader() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return readerRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Reader not found for the email;" + email));
    }

    @Override
    public Page<ReaderProfileResponseDTO> getAllReader(Pageable page) {
        return readerRepository.findAllReader(page);
    }

    @Override
    public ReaderProfileResponseDTO addFunds(Reader reader, FundsDTO funds) {
        InputReaderProfileDTO inputProfileDTO = new InputReaderProfileDTO();
        BigDecimal stake = BigDecimal.valueOf(funds.getMoney());
        reader.setBalance(reader.getBalance().add(stake));
        BeanUtils.copyProperties(reader, inputProfileDTO );
        ReaderProfileResponseDTO responseDTO = new ReaderProfileResponseDTO(inputProfileDTO);
        return responseDTO;
    }


}
