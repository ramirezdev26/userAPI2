package com.capstone.users.domain.exceptions.userExceptions;

public class UserEmptyDataException extends RuntimeException {
    public UserEmptyDataException(String message) {
        super(message);
    }
}
