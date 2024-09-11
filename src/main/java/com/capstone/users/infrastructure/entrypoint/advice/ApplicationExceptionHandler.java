package com.capstone.users.infrastructure.entrypoint.advice;

import com.capstone.users.domain.exceptions.CustomersNotFoundException;
import com.capstone.users.domain.exceptions.InvalidUserDataException;
import com.capstone.users.domain.exceptions.UserAlreadyExistsException;
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

    private ProblemDetail build(HttpStatus status, Exception ex, Consumer<ProblemDetail> consumer) {
        var problem = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        consumer.accept(problem);
        return problem;
    }

    @ExceptionHandler(InvalidUserDataException.class)
    public ProblemDetail handleException(InvalidUserDataException ex) {
        return build(HttpStatus.BAD_REQUEST, ex, problem -> {
            problem.setType(URI.create("http://capstone.com/users/invalid-user-data"));
            problem.setTitle("Invalid User Data");
        });
    }
}