package com.capstone.users.infrastructure.entrypoint.advice;

import com.capstone.users.domain.exceptions.CustomersNotFoundException;
import com.capstone.users.domain.exceptions.userExceptions.UserAlreadyExistsException;
import com.capstone.users.domain.exceptions.userExceptions.UserEmptyDataException;
import com.capstone.users.domain.exceptions.userExceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.util.function.Consumer;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(CustomersNotFoundException.class)
    public ProblemDetail handleException(CustomersNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex, problem -> {
            problem.setType(URI.create("http://capstone.com/users/customer-not-found"));
            problem.setTitle("Customer Not Found");
        });
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ProblemDetail handleException(UserAlreadyExistsException ex) {
        return build(HttpStatus.BAD_REQUEST, ex, problem -> {
            problem.setType(URI.create("http://capstone.com/users/user-already-exist"));
            problem.setTitle("User Already Exist");
        });
    }

    @ExceptionHandler(UserEmptyDataException.class)
    public ProblemDetail handleException(UserEmptyDataException ex) {
        return build(HttpStatus.BAD_REQUEST, ex, problem -> {
            problem.setType(URI.create("http://capstone.com/users-empty-data"));
            problem.setTitle("The user cannot have empty data");
        });
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleException(UserNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex, problem -> {
            problem.setType(URI.create("http://capstone.com/users/user-not-found"));
            problem.setTitle("User Not Found");
        });
    }

    private ProblemDetail build(HttpStatus status, Exception ex, Consumer<ProblemDetail> consumer) {
        var problem = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        consumer.accept(problem);
        return problem;
    }
}