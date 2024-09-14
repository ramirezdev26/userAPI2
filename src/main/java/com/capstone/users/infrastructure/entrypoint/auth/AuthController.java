package com.capstone.users.infrastructure.entrypoint.auth;
import com.capstone.users.domain.model.User;
import com.capstone.users.infrastructure.entrypoint.auth.dto.AuthResponse;
import com.capstone.users.infrastructure.entrypoint.auth.dto.LoginResquest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Handles the login request and returns a ResponseEntity containing the AuthResponse.
     *
     * @param  Resquest  the LoginResquest object containing the login credentials
     * @return           a ResponseEntity containing the AuthResponse
     */
    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginResquest Resquest) {
        return ResponseEntity.ok(authService.login(Resquest));
    }

    /**
     * Handles the registration request and returns a ResponseEntity containing the AuthResponse.
     *
     * @param  Resquest  the User object containing the registration data
     * @return           a ResponseEntity containing the AuthResponse
     */
    @PostMapping(value = "/register")
    public ResponseEntity<AuthResponse> register(@RequestBody User Resquest) {
        return ResponseEntity.ok(authService.register(Resquest));
    }
}
