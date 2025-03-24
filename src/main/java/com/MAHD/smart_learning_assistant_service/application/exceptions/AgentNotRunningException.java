package com.MAHD.smart_learning_assistant_service.application.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class AgentNotRunningException extends ResponseStatusException {
    private static final Logger logger = LoggerFactory.getLogger(AgentNotRunningException.class);

    public AgentNotRunningException(String message) {
        super(HttpStatus.SERVICE_UNAVAILABLE, message);
        logger.error("Agent Not Running Exception: {}", this.getMessage());

    }
}