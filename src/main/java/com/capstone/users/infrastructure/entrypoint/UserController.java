package com.capstone.users.infrastructure.entrypoint;

import com.capstone.users.domain.model.User;
import com.capstone.users.domain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.ok(user);
    }
}
