package com.capstone.users.domain.service;

import com.capstone.users.domain.exceptions.ApplicationExceptions;
import com.capstone.users.domain.exceptions.CustomersNotFoundException;
import com.capstone.users.domain.exceptions.userExceptions.UserAlreadyExistsException;
import com.capstone.users.domain.exceptions.userExceptions.UserEmptyDataException;
import com.capstone.users.domain.exceptions.userExceptions.UserNotFoundException;
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
        validateUserEmptyData(user);

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
                .orElseThrow(UserNotFoundException::new);
       if (!existingUser.getLogin().equals(updatedUser.getLogin()) && findByLogin(updatedUser.getLogin()).isPresent()) {
            ApplicationExceptions.userAlreadyExistException();
        }

        existingUser.setName(updatedUser.getName());
        existingUser.setLogin(updatedUser.getLogin());
        existingUser.setPassword(updatedUser.getPassword());

        return userRepository.update(existingUser);
    }

    /**
     * Deletes a user by ID from the repository.
     * Before deleting, it checks if the provided ID is valid and if the user exists in the repository.
     *
     * @param id The ID of the user to delete.
     * @return A success message when the user is deleted successfully.
     * @throws UserEmptyDataException If the user ID is null or empty.
     * @throws UserNotFoundException If the user with the given ID does not exist.
     */
    public String deleteById(String id) {
      if (id == null || id.trim().isEmpty()) {
        ApplicationExceptions.idUserIsNull("User ID cannot be null or empty");
      }
      Optional<User> user = userRepository.findById(id);
      if (user.isEmpty()) {
        ApplicationExceptions.userNotFoundException();
      }
      userRepository.deleteById(id);
      return "User with ID: " + id + " deleted successfully";
    }

    /**
     * <p>
     * This method checks that the `name`, `login` and `password` fields
     * of the provided `User` object are not empty or null.
     * <p>
     *
     * @param user The User object to be validated.
     * @throws UserEmptyDataException If any of the required fields are null or empty.
     */
    private void validateUserEmptyData(User user)
    {
        if (StringUtils.isNullOrEmpty(user.getName()))
        {
            ApplicationExceptions.userEmptyDataException("User name cannot be empty");
        }

        if (StringUtils.isNullOrEmpty(user.getLogin()))
        {
            ApplicationExceptions.userEmptyDataException("User login cannot be empty");
        }

        if (StringUtils.isNullOrEmpty(user.getPassword()))
        {
            ApplicationExceptions.userEmptyDataException("User password cannot be empty");
        }
    }
}
