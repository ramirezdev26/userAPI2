package com.capstone.users.domain.exceptions;

import com.capstone.users.domain.exceptions.userExceptions.UserAlreadyExistsException;
import com.capstone.users.domain.exceptions.userExceptions.UserEmptyDataException;
import com.capstone.users.domain.exceptions.userExceptions.UserNotFoundException;

/**
 * The ApplicationExceptions class acts as a centralized location for custom domain-specific exceptions.
 * It provides methods to throw exceptions, allowing for consistent and reusable exception handling throughout the system.
 */
public class ApplicationExceptions {

    /**
     * Throws a CustomersNotFoundException when a customer entity cannot be located in the system.
     */
    public static void customersNotFound(){
        throw new CustomersNotFoundException();
    }

    /**
     * Throws a UserAlreadyExistException when there is an entity with the same login.
     */
    public static void userAlreadyExistException(){throw new UserAlreadyExistsException();}

    /**
     * Throws a UserNotFound exception when the requested user entity cannot be located in the system.
     */
    public static void userNotFoundException(){
        throw new UserNotFoundException();
    }

    /**
     * Trows a UserEmptyDataException when some user data is empty (name, login, password).
     */
    public static void userNotFound(){
        throw new UserNotFound();
    }

    /**
     * Throws an InvalidUserDataException when invalid or empty user data is encountered.
     *
     * @param message A message describing the specific invalid user data issue.
     */
    public static void userEmptyDataException(String message) {
        throw new UserEmptyDataException(message);
    }

    /**
     * Throws an IllegalArgumentException when the user ID is null.
     *
     * @param message A message describing the reason why the user ID is null.
     */
    public static void idUserIsNull(String message) {
        throw new IllegalArgumentException(message);
    }
}

