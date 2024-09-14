package com.capstone.users.infrastructure.entrypoint.auth;

import com.capstone.users.domain.model.User;
import com.capstone.users.domain.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Controller", description = "Public routes")
public class AuthController {

    private final UserService userService;

    @PostMapping(value = "/login")
    public String login() {
        return "Login from public endpoint";
    }

    @Operation(summary = "User Register")
    @PostMapping(value = "/register")
    public ResponseEntity<User> register(@Parameter(description = "User object", required = true)
                                             @RequestBody User user) {
        userService.save(user);
        return ResponseEntity.ok(user);
    }
}
