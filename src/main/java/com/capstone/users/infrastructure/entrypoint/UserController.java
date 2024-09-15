package com.capstone.users.infrastructure.entrypoint;

import com.capstone.users.domain.model.User;
import com.capstone.users.domain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<User> update(@PathVariable String id, @RequestBody User user) {
        User updatedUser = userService.update(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        String response = userService.deleteById(id);
        return ResponseEntity.ok(response);
    }
}
