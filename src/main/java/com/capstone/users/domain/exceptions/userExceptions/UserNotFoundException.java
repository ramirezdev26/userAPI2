package com.capstone.users.domain.exceptions.userExceptions;

public class UserNotFoundException extends RuntimeException {

    private static final String MESSAGE = "User is not found";

    public UserNotFoundException() {
        super(MESSAGE);
    }

}
