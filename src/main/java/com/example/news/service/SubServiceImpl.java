package com.example.news.service;


import com.example.news.dto.SaveSubRequest;
import com.example.news.dto.SubResponseDTO;
import com.example.news.entity.Author;
import com.example.news.entity.Reader;
import com.example.news.entity.Subscription;
import com.example.news.exception.NotFoundEx;
import com.example.news.repository.SubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    public SubResponseDTO createSub(SaveSubRequest subModel) throws NotFoundEx {
        if (authorService.existByIdAuthor(subModel.getIdAuthor())) {
            Author author = authorService.findAuthorById(subModel.getIdAuthor());
            Reader reader = readerService.getLoggedInReader();

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

    private Subscription setSubDetails(Author author, Reader reader) {
        Subscription subscription = new Subscription();
        subscription.setAuthor(author);
        subscription.setReader(reader);
        subscription.setEnd_date(addDay());
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

}
