package com.capstone.users.infrastructure.entrypoint;

import com.capstone.users.domain.model.User;
import com.capstone.users.domain.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
@Tag(name = "User Controller", description = "Protected routes")
public class UserController {

    private UserService userService;

    @Operation(summary = "User Update")
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<User> update(@Parameter(description = "User ID to be updated") @PathVariable String id,
                                       @Parameter(description = "User object") @RequestBody User user) {
        User updatedUser = userService.update(id, user);
        return ResponseEntity.ok(updatedUser);
    }
}
