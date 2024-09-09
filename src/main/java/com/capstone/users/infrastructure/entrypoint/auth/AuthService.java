package com.capstone.users.infrastructure.entrypoint.auth;

import com.capstone.users.domain.model.User;
import com.capstone.users.domain.service.UserService;
import com.capstone.users.infrastructure.entrypoint.auth.dto.AuthResponse;
import com.capstone.users.infrastructure.entrypoint.auth.dto.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        String token = jwtService.getToken(userService.findByUsername(request.getUsername())
                .map(userFound -> UserAuth.builder()
                        .id(userFound.getId())
                        .name(userFound.getName())
                        .login(userFound.getLogin())
                        .password(userFound.getPassword())
                        .build())
                .orElseThrow());
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(User request) {
        User userSaved = userService.save(User.builder()
                .id(request.getId())
                .name(request.getName())
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .build());

        return AuthResponse.builder()
                .token(jwtService.getToken(UserAuth.builder()
                        .id(userSaved.getId())
                        .name(userSaved.getName())
                        .login(userSaved.getLogin())
                        .password(userSaved.getPassword())
                        .build()))
                .build();

    }

}
