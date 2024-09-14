package com.capstone.users.domain.exceptions.userExceptions;

public class AuthFailedException extends RuntimeException {
  private static final String MESSAGE = "Invalid credentials";

  public AuthFailedException() {
    super(MESSAGE);
  }
}
