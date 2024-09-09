package com.capstone.users.infrastructure.entrypoint;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UserController {

    @PostMapping(value = "/register")
    public String register() {
        return "Register from secure endpoint";
    }

}
