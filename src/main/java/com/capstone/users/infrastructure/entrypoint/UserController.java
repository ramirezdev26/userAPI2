package com.capstone.users.infrastructure.entrypoint;

import com.capstone.users.domain.model.User;
import com.capstone.users.domain.service.UserService;
import java.util.List;
import java.util.Optional;
import javax.swing.text.html.Option;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id){
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/get")
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }
}
