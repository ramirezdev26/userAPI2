package com.capstone.users.domain.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    private static final String MESSAGE = "User already exists";

    public UserAlreadyExistsException() {
        super(MESSAGE);
    }
}
