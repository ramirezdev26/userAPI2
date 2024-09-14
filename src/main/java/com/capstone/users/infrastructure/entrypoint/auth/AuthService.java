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

  public AuthResponse login(LoginResquest loginResquest) {

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
