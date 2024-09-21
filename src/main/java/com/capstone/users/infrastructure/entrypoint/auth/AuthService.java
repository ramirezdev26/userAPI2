package com.capstone.users.infrastructure.entrypoint.auth;

import com.capstone.users.domain.exceptions.ApplicationExceptions;
import com.capstone.users.domain.model.User;
import com.capstone.users.domain.service.UserService;
import com.capstone.users.infrastructure.entrypoint.auth.dto.AuthResponse;
import com.capstone.users.infrastructure.entrypoint.auth.dto.LoginResquest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {


  private final UserService userService;
  private final JwtService jwtService;
  private final AuthenticationManager authManager;
  private final PasswordEncoder passwordEncoder;

  /**
   * Logs in a user with the provided login request and returns an authentication response containing a JWT token.
   *
   * @param  loginResquest  the login request containing the user's login and password
   * @return                an authentication response containing a JWT token
   * @throws RuntimeException if the user is not found
   * @throws AuthenticationException if the password does not match the user's password
   */
  public AuthResponse login(LoginResquest loginResquest) {
    PwdValidator pwdValidator = new PwdValidator();
    User user = userService.findByLogin(loginResquest.getLogin())
        .orElseThrow();

    if (!pwdValidator.isPasswordEncrypted(user.getPassword()) &&
        loginResquest.getPassword().equals(user.getPassword())) {
        updatePassword(user, loginResquest.getPassword());
    }

    try {
      authManager.authenticate(new UsernamePasswordAuthenticationToken(loginResquest.getLogin(), loginResquest.getPassword()));
    } catch (AuthenticationException e) {
      ApplicationExceptions.authFailedException();
    }

    String token = jwtService.getToken(userService.findByLogin(loginResquest.getLogin())
        .map(userFound -> UserAuth.builder()
            .id(userFound.getId())
            .login(userFound.getLogin())
            .password(userFound.getPassword())
            .name(userFound.getName())
            .build())
        .orElseThrow());
    return AuthResponse.builder()
        .token(token)
        .build();
  }

  /**
   * Updates the password of a user in the database and returns the updated user object.
   *
   * @param  user      the user object whose password needs to be updated
   * @param  password  the new password to be set for the user
   * @return           the updated user object with the new password
   */
  private User updatePassword(User user, String password) {
    User updatedUser = User.builder().
        id(user.getId()).
        name(user.getName()).
        login(user.getLogin()).
        password(passwordEncoder.encode(password)).build();
    return userService.update(user.getId(), updatedUser);
  }

  /**
   * Registers a new user with the provided request and returns an authentication response containing a JWT token.
   *
   * @param  request  the user registration request containing the user's id, name, login, and password
   * @return          an authentication response containing a JWT token
   */
  public AuthResponse register(User request) {
    User user = userService.save(User.builder()
            .id(request.getId())
            .name(request.getName())
            .login(request.getLogin())
            .password(passwordEncoder.encode(request.getPassword()))
        .build());
    return AuthResponse.builder()
        .token(jwtService.getToken(UserAuth.builder()
            .id(user.getId())
            .name(user.getName())
            .login(user.getLogin())
            .password(user.getPassword())
            .build()))
        .build();
  }
}
