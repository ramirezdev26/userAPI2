package com.capstone.users.infrastructure.drivenadapter;

import com.capstone.users.domain.exceptions.ApplicationExceptions;
import com.capstone.users.domain.model.User;
import com.capstone.users.domain.model.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * The UserRepositoryAdapter class serves as an adapter between the domain layer and the persistence layer (driven adapter).
 * <p>
 * It is responsible for bridging the gap between the domain's UserRepository interface and the implementation provided by
 * the UserMySQLRepository. By doing so, it ensures the domain layer remains independent of the underlying persistence mechanism.
 * <p>
 * This class is annotated with @Component, indicating that it is a Spring-managed bean that can be injected where needed.
 * The @AllArgsConstructor annotation automatically generates a constructor with arguments for all fields, enabling easy dependency injection.
 */
@AllArgsConstructor
@Component
public class UserRepositoryAdapter implements UserRepository {

    private final UserMySQLRepository userMySQLRepository;

    /**
     * Saves a User domain object to the MySQL repository.
     *
     * @param user Represents the User domain object to be saved.
     */
    @Override
    public User save(User user) {
        UserData userData = mapFrom(user);
        return mapTo(userMySQLRepository.save(userData));
    }

    /**
     * Finds a user by their login.
     *
     * @param login Is the login identifier of the user.
     * @return An Optional containing the User if found, or empty if the user does not exist.
     */
    @Override
    public Optional<User> findByLogin(String login) {
        return userMySQLRepository.findByLogin(login)
                .map(this::mapTo);
    }

    @Override
    public Optional<User> findById(String id) {
        return userMySQLRepository.findById(id)
                .map(this::mapTo);
    }

    @Override
    public User update(User user) {
        Optional<UserData> existingUserData = userMySQLRepository.findById(user.getId());

        if (existingUserData.isEmpty()) {
            ApplicationExceptions.userNotFound();
        }
        UserData userData = mapFrom(user);

        return mapTo(userMySQLRepository.save(userData));
    }


    /**
     * Converts the UserData entity into a User domain model object.
     *
     * @return A User domain model object with the data from this entity.
     */
    private User mapTo(UserData userData) {
        return User.builder()
                .id(userData.getId())
                .name(userData.getName())
                .login(userData.getLogin())
                .password(userData.getPassword())
                .build();
    }

    /**
     * Converts the User domain model object into a UserData entity.
     *
     * @param user domain model object to convert.
     * @return A UserData entity with the data from the user domain model object.
     */
    private UserData mapFrom(User user) {
        UserData userData = new UserData();
        userData.setId(user.getId());
        userData.setName(user.getName());
        userData.setLogin(user.getLogin());
        userData.setPassword(user.getPassword());
        return userData;
    }
}
