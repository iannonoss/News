package com.democom.news.dto;

import lombok.Data;
import java.util.Date;

@Data

public class SubsDTO {


    private String person;

    private Boolean stateSubscription;

    private Date start_date;

    private Date end_date;

    public SubsDTO(String reader, Boolean stateSubscription, Date start_date, Date end_date) {
        this.person = reader;
        this.stateSubscription = stateSubscription;
        this.start_date = start_date;
        this.end_date = end_date;
    }
}
