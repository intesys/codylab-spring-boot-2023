package it.intesys.academy.controller.rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.net.URI;
import java.time.Instant;

public class ProjectAccessException extends ErrorResponseException {

    public ProjectAccessException(String message, Integer projectId) {
        super(HttpStatus.FORBIDDEN, asProblemDetail(message, projectId), null);
    }

    private static ProblemDetail asProblemDetail(String message, Integer projectId) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, message);
        problemDetail.setTitle("Project permission error");
        problemDetail.setType(URI.create("https://api.issuetracker.com/errors/project-permission-error"));
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("projectId",projectId);
        return problemDetail;
    }
}