package com.capstone.users.domain.exceptions;

public class UserNotFound extends RuntimeException {

    private static final String MESSAGE = "User is not found";

    public UserNotFound() {
        super(MESSAGE);
    }

}
