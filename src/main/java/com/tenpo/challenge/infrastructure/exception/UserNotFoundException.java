package com.tenpo.challenge.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String exception) {
        super(exception);
    }

}
