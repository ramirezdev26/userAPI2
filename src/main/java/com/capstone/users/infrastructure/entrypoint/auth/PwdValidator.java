package com.capstone.users.infrastructure.entrypoint.auth;

public class PwdValidator {

  /**
   * Checks if the given password is encrypted with BCrypt.
   *
   * @param password the password to check
   * @return true if the password is encrypted with BCrypt, false otherwise
   */
  public boolean isPasswordEncrypted(String password) {
    return password.startsWith("$2a$") || password.startsWith("$2b$") || password.startsWith("$2y$");
  }

}
