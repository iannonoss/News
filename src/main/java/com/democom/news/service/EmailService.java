package com.democom.news.service;

import com.democom.news.dto.EmailDetails;

import java.security.SecureRandom;

public interface EmailService {

    String sendSimpleMail(EmailDetails details);

    String sendMailWithAttachment(EmailDetails details);

    String  sendLink(EmailDetails details);
}
