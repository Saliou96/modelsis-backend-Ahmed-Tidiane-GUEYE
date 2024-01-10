package com.example.test.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class RessourceNotFoundException extends ResponseStatusException {
    public RessourceNotFoundException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
