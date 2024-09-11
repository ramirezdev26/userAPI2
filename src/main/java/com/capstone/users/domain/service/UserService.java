package com.capstone.users.domain.service;

import com.capstone.users.domain.exceptions.ApplicationExceptions;
import com.capstone.users.domain.exceptions.CustomersNotFoundException;
import com.capstone.users.domain.exceptions.UserAlreadyExistsException;
import com.capstone.users.domain.exceptions.UserNotFound;
import com.capstone.users.domain.model.User;
import com.capstone.users.domain.model.UserRepository;
import com.capstone.users.utils.StringUtils;
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
     * Finds a user by their ID.
     *
     * @param id The ID of the user to find.
     * @return An Optional containing the User if found, or an empty Optional if the user does not exist.
     */
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
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

    /**
     * Updates an existing user in the repository.
     * Before updating, it checks if the provided data is valid and if the login does not conflict with another user.
     *
     * @param id          The ID of the user to update.
     * @param updatedUser The User object containing the updated details.
     * @return The updated User object.
     * @throws CustomersNotFoundException If the user with the given ID does not exist.
     * @throws UserAlreadyExistsException If another user with the same login exists.
     */
    public User update(String id, User updatedUser) {
       validateUserEmptyData(updatedUser);

       User existingUser = findById(id)
                .orElseThrow(UserNotFound::new);
       if (!existingUser.getLogin().equals(updatedUser.getLogin()) && findByLogin(updatedUser.getLogin()).isPresent()) {
            ApplicationExceptions.userAlreadyExistException();
        }

        existingUser.setName(updatedUser.getName());
        existingUser.setLogin(updatedUser.getLogin());
        existingUser.setPassword(updatedUser.getPassword());

        return userRepository.update(existingUser);
    }

    private void validateUserEmptyData(User user)
    {
        if (StringUtils.isNullOrEmpty(user.getName()))
        {
            ApplicationExceptions.invalidUserDataException("User name cannot be empty");
        }

        if (StringUtils.isNullOrEmpty(user.getLogin()))
        {
            ApplicationExceptions.invalidUserDataException("User login cannot be empty");
        }

        if (StringUtils.isNullOrEmpty(user.getPassword()))
        {
            ApplicationExceptions.invalidUserDataException("User password cannot be empty");
        }
    }
}
