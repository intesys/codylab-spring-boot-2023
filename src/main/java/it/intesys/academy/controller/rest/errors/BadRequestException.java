package it.intesys.academy.controller.rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.net.URI;
import java.time.Instant;

public class BadRequestException extends ErrorResponseException {

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, asProblemDetail(message), null);
    }

    private static ProblemDetail asProblemDetail(String message) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, message);
        problemDetail.setTitle("Project permission error");
        problemDetail.setType(URI.create("https://api.issuetracker.com/errors/bad-request"));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}

