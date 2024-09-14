package com.capstone.users.infrastructure.entrypoint;

import com.capstone.users.domain.model.User;
import com.capstone.users.domain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final  PasswordEncoder passwordEncoder;
    @PostMapping(value = "/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<User> update(@PathVariable String id, @RequestBody User user) {
        User updatedUser = userService.update(id, user.toBuilder().password(passwordEncoder.encode(user.getPassword())).build());
        return ResponseEntity.ok(updatedUser);
    }
}
