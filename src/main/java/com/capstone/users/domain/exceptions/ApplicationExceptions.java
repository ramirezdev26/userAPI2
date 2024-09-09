package com.capstone.users.domain.exceptions;

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

}
