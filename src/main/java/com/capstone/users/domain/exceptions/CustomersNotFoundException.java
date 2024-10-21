package com.capstone.users.domain.exceptions;

/**
 * CustomersNotFoundException is a custom runtime exception that is thrown when a customer entity is not found.
 * <p>
 * This class extends RuntimeException and provides a custom error message indicating the absence of customers.
 */
public class CustomersNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Customers is not found";

    public CustomersNotFoundException() {
        super(MESSAGE);
    }

}
