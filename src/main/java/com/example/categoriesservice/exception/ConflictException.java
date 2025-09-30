package com.example.categoriesservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.time.Instant;
import java.util.Map;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
      super(message);
    }

}

