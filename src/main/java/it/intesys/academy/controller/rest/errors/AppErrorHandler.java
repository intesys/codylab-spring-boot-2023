package it.intesys.academy.controller.rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
@RestControllerAdvice

public class AppErrorHandler  extends ResponseEntityExceptionHandler {

        @ExceptionHandler(RuntimeException.class)
        ProblemDetail handleGenericException(RuntimeException e) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            problemDetail.setTitle(e.getMessage());
            problemDetail.setType(URI.create("https://api.issuetracker.com/errors/internal-server-error"));
            return problemDetail;
        }
    }


