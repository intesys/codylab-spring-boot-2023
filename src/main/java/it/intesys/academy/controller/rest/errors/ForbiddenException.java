package it.intesys.academy.controller.rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.net.URI;
import java.time.Instant;

public class ForbiddenException extends ErrorResponseException {
    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, asProblemDetail(message), null);
    }

    private static ProblemDetail asProblemDetail(String message) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, message);
        problemDetail.setTitle("Forbidden error");
        problemDetail.setType(URI.create("https://api.issuetracker.com/errors/forbidden-error"));
        problemDetail.setProperty("timestamp", Instant.now());
        //problemDetail.setProperty("projectId",projectId);
        return problemDetail;
    }
}
