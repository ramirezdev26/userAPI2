package com.capstone.users.infrastructure.entrypoint.auth;

import com.capstone.users.domain.model.User;
import com.capstone.users.domain.service.UserService;
import com.capstone.users.infrastructure.entrypoint.auth.dto.AuthToken;
import com.capstone.users.infrastructure.entrypoint.auth.dto.AuthTokenResponse;
import com.capstone.users.infrastructure.entrypoint.auth.dto.LoginResquest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Controller", description = "Public routes")
public class AuthController {


    private final AuthService authService;

    /**
     * Handles the login request and returns a ResponseEntity containing the AuthResponse.
     *
     * @param Resquest  the LoginResquest object containing the login credentials
     * @return a ResponseEntity containing the AuthResponse
     */
    private final UserService userService;

    @PostMapping(value = "/login")
    public ResponseEntity<AuthToken> login(@RequestBody LoginResquest Resquest) {
        return ResponseEntity.ok(authService.login(Resquest));
    }


    /**
     * Handles the registration request and returns a ResponseEntity containing the AuthResponse.
     *
     * @param user the User object containing the registration data
     * @return a ResponseEntity containing the AuthResponse
     */
    @Operation(summary = "User Register")
    @PostMapping(value = "/register")
    public ResponseEntity<AuthToken> register(@Parameter(description = "User object", required = true)
                                              @RequestBody User user) {
        return ResponseEntity.ok(authService.register(user));
    }

    /**
     * Handles the token request and returns a ResponseEntity containing the AuthTokenResponse.
     *
     * @param authToken the AuthToken object containing the token
     * @return a ResponseEntity containing the AuthTokenResponse
     */
    @PostMapping(value = "/token")
    public ResponseEntity<AuthTokenResponse> token(@RequestBody AuthToken authToken) {
        return ResponseEntity.ok(authService.token(authToken));
    }
}
