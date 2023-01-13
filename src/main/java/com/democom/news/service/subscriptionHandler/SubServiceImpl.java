package com.democom.news.service.subscriptionHandler;


import com.democom.news.dto.SaveSubRequestDTO;
import com.democom.news.dto.SubResponseDTO;
import com.democom.news.dto.SubsDTO;
import com.democom.news.entity.Author;
import com.democom.news.entity.Reader;
import com.democom.news.entity.Subscription;
import com.democom.news.exception.NotFoundEx;
import com.democom.news.exception.UnauthorizedInstructionException;
import com.democom.news.repository.SubRepository;
import com.democom.news.service.auhtorHandler.IAuthorService;
import com.democom.news.service.readerHandler.IReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubServiceImpl implements ISubService {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    @Autowired
    private IAuthorService authorService;

    @Autowired
    private IReaderService readerService;


    @Autowired
    private SubRepository subRepository;


    @Override
    public SubResponseDTO createSub(SaveSubRequestDTO subModel, Reader reader) throws NotFoundEx {
        if (authorService.existByIdAuthor(subModel.getIdAuthor())) {
            Author author = authorService.findAuthorById(subModel.getIdAuthor());
            verifyBalanceUser(reader, author.getSubscription_price());
            reader.setBalance(BigDecimal.valueOf(reader.getBalance().doubleValue() - author.getSubscription_price().doubleValue()));
            if(subRepository.existsByAuthorAndReaderAndStateSubscription(author, reader, true)){
                throw new Error("You cannot subscribe to an author you are already subscribed to");
            }
            Subscription save = subRepository.save(setSubDetails(author, reader));
            return buildResponseDTO(save);
        }
        throw new NotFoundEx("Author not found with id:"+subModel.getIdAuthor());
    }


    private Date addDay() {
        Date currentDate = new Date();
        System.out.println(dateFormat.format(currentDate));
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DAY_OF_YEAR, 30);
        return c.getTime();
    }

    private Date togli() {
        Date currentDate = new Date();
        System.out.println(dateFormat.format(currentDate));
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        return c.getTime();
    }

    private Subscription setSubDetails(Author author, Reader reader) {
        Subscription subscription = new Subscription();
        subscription.setAuthor(author);
        subscription.setReader(reader);
        subscription.setEnd_date(addDay() );
        subscription.setStateSubscription(true);
        return subscription;
    }


    private SubResponseDTO buildResponseDTO(Subscription save) {
        SubResponseDTO subResponseDTO = new SubResponseDTO();
        subResponseDTO.setId(save.getId());
        subResponseDTO.setReaderId(save.getReader().getId().toString());
        subResponseDTO.setAuthorId(save.getAuthor().getId().toString());
        subResponseDTO.setState_subscription(save.getStateSubscription());
        subResponseDTO.setStart_date(save.getStart_date());
        subResponseDTO.setEnd_Date(save.getEnd_date());
        return subResponseDTO;
    }

    private void verifyBalanceUser(Reader reader, BigDecimal price) {
        if (reader.getBalance().longValue() < price.longValue()) {
            throw new UnauthorizedInstructionException("You don't have enough money");
        }
    }

    @Override
    public Page<SubsDTO> getSubsAuthor(Pageable page, Long id) {
       List<Subscription> subscriptions = subRepository.findSubscriptionByAuthorId(id);
       List<SubsDTO> subsDTOS = subscriptions.stream().map(s -> new SubsDTO(s.getReader().getName()+" "+s.getReader().getSurname(), s.getStateSubscription(), s.getStart_date(), s.getEnd_date())).collect(Collectors.toList());
        return new PageImpl<>(subsDTOS, page, subsDTOS.size());
    }

    @Override
    public Page<SubsDTO> getSubsReader(Pageable page, Long id) {
        List<Subscription> subscriptions = subRepository.findSubscriptionByReaderId(id);
        List<SubsDTO> subsDTOS = subscriptions.stream().map(s -> new SubsDTO(s.getAuthor().getName()+" "+s.getAuthor().getSurname(), s.getStateSubscription(), s.getStart_date(), s.getEnd_date())).collect(Collectors.toList());
        return new PageImpl<>(subsDTOS, page, subsDTOS.size());
    }


}
