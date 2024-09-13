package com.capstone.users.domain.exceptions.userExceptions;

public class UserAlreadyExistsException extends RuntimeException {

    private static final String MESSAGE = "User already exists";

    public UserAlreadyExistsException() {
        super(MESSAGE);
    }
}
