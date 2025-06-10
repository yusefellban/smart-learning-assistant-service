package com.MAHD.smart_learning_assistant_service.application.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends ResponseStatusException {
    private static final Logger logger = LoggerFactory.getLogger(UserNotFoundException.class);

    public UserNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
        logger.warn("User Not Found Exception: {}", this.getMessage());

    }
}
