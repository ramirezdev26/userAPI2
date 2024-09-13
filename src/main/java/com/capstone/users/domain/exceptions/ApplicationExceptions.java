package com.capstone.users.domain.exceptions;

import com.capstone.users.domain.exceptions.userExceptions.UserAlreadyExistsException;
import com.capstone.users.domain.exceptions.userExceptions.UserEmptyDataException;

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
     * Trows a UserEmptyDataException when some user data is empty (name, login, password).
     */
    public static void userEmptyDataException(String message){throw new UserEmptyDataException(message);}
}
