package com.capstone.users.infrastructure.entrypoint.auth;

import com.capstone.users.domain.exceptions.ApplicationExceptions;
import com.capstone.users.domain.exceptions.userExceptions.AuthFailedException;
import com.capstone.users.domain.exceptions.userExceptions.UserNotFoundException;
import com.capstone.users.domain.model.User;
import com.capstone.users.domain.service.UserService;
import com.capstone.users.infrastructure.entrypoint.auth.dto.AuthToken;
import com.capstone.users.infrastructure.entrypoint.auth.dto.AuthTokenResponse;
import com.capstone.users.infrastructure.entrypoint.auth.dto.LoginResquest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserService userService;
  private final JwtService jwtService;
  private final AuthenticationManager authManager;
  private final PasswordEncoder passwordEncoder;
  private final UserDetailsService userDetailsService;


    /**
   * Logs in a user with the provided login request and returns an authentication response containing a JWT token.
   *
   * @param  loginResquest  the login request containing the user's login and password
   * @return                an authentication response containing a JWT token
   * @throws RuntimeException if the user is not found
   * @throws AuthenticationException if the password does not match the user's password
   */
  public AuthToken login(LoginResquest loginResquest) {
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
    return AuthToken.builder()
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
  public AuthToken register(User request) {
    User user = userService.save(User.builder()
            .id(request.getId())
            .name(request.getName())
            .login(request.getLogin())
            .password(passwordEncoder.encode(request.getPassword()))
        .build());
    return AuthToken.builder()
        .token(jwtService.getToken(UserAuth.builder()
            .id(user.getId())
            .name(user.getName())
            .login(user.getLogin())
            .password(user.getPassword())
            .build()))
        .build();
  }

    /**
     * Validates the token and returns the user details if the token is valid.
     *
     * @param  authToken  the authentication token containing the JWT token
     * @return            the user details if the token is valid
     * @throws AuthFailedException if the token is invalid
     * @throws UsernameNotFoundException if the username is not found
     * @throws UserNotFoundException if the user is not found
     */
  public AuthTokenResponse token(AuthToken authToken) {

      try {
          String username = jwtService.getUsernameFromToken(authToken.getToken());
          UserDetails userDetails = userDetailsService.loadUserByUsername(username);
          if (jwtService.isTokenValid(authToken.getToken(), userDetails)) {
            User userAuthenticated = userService.findByLogin(username).orElseThrow(UserNotFoundException::new);
            return AuthTokenResponse.builder()
                    .id(userAuthenticated.getId())
                    .name(userAuthenticated.getName())
                    .login(userAuthenticated.getLogin())
                    .build();
          } else {
            throw new AuthFailedException();
          }
      } catch (UsernameNotFoundException e) {
          throw new UsernameNotFoundException(e.getMessage());
      } catch (UserNotFoundException e) {
          throw new UserNotFoundException();
      } catch (RuntimeException e) {
          throw new AuthFailedException();
      }
  }
}
