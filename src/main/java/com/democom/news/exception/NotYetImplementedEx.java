package com.democom.news.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
public class NotYetImplementedEx extends Exception {
  private static final long serialVersionUID = 1L;


  public NotYetImplementedEx(String message) {
    super(message);
  }
}
