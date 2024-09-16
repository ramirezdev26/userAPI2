package com.capstone.users.infrastructure.entrypoint;

import com.capstone.users.domain.model.User;
import com.capstone.users.domain.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
@Tag(name = "User Controller", description = "Protected routes")
public class UserController {


    private final UserService userService;
    private final  PasswordEncoder passwordEncoder;

    @Operation(summary = "User Update")
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<User> update(@Parameter(description = "User ID to be updated") @PathVariable String id,
                                       @Parameter(description = "User object") @RequestBody User user) {
            User updatedUser = userService.update(id, user.toBuilder().password(passwordEncoder.encode(user.getPassword())).build());
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "User Delete")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> delete(@Parameter(description = "User ID to be deleted") @PathVariable String id) {
        String response = userService.deleteById(id);
        return ResponseEntity.ok(response);
    }
}
