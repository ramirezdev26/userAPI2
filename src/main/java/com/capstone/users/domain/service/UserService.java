package com.capstone.users.domain.service;

import com.capstone.users.domain.exceptions.ApplicationExceptions;
import com.capstone.users.domain.exceptions.UserAlreadyExistsException;
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

    /**
     * Finds a user by their login.
     *
     * @param login Is the Login of the user to find.
     * @return An Optional containing the User if found, or empty if the user does not exist.
     */
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    /**
     * Saves a new user to the repository.
     * Before saving, it validates if a user with the same login already exists.
     *
     * @param user Represents the User object containing the details to be saved.
     * @throws UserAlreadyExistsException If a user with the same login already exists.
     */
    public User save(User user) {
        if (findByLogin(user.getLogin()).isPresent()) {
            ApplicationExceptions.userAlreadyExistException();
        }

        return userRepository.save(user.toBuilder()
                .id(UUID.randomUUID().toString())
                .name(user.getName())
                .login(user.getLogin())
                .password(user.getPassword())
                .build());
    }
}
