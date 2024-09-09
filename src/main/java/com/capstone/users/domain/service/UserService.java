package com.capstone.users.domain.service;

import com.capstone.users.domain.model.User;
import com.capstone.users.domain.model.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * The UserService class provides core business logic related to the User entity.
 * It acts as a use case layer, processing operations like user management, validation, and other business-specific tasks.
 * <p>
 * This class is annotated with @Service, indicating that it is a Spring service component that can be injected where needed.
 */
@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User newUser) {
        return userRepository.save(newUser.toBuilder().id(UUID.randomUUID().toString()).build());
    }
}
