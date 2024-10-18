package com.capstone.users.infrastructure.drivenadapter;

import com.capstone.users.domain.exceptions.ApplicationExceptions;
import com.capstone.users.domain.model.User;
import com.capstone.users.domain.model.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private final UserMongoRepository userMongoRepository;

    @Override
    public User save(User user) {
        UserData userData = mapFrom(user);
        return mapTo(userMongoRepository.save(userData));
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userMongoRepository.findByLogin(login)
            .map(this::mapTo);
    }

    @Override
    public Optional<User> findById(String id) {
        return userMongoRepository.findById(id)
            .map(this::mapTo);
    }

    @Override
    public List<User> findAll() {
        return userMongoRepository.findAll().stream()
            .map(this::mapTo)
            .collect(Collectors.toList());
    }

    @Override
    public User update(User user) {
        Optional<UserData> existingUserData = userMongoRepository.findById(user.getId());

        if (existingUserData.isEmpty()) {
            ApplicationExceptions.userNotFoundException();
        }
        UserData userData = mapFrom(user);

        return mapTo(userMongoRepository.save(userData));
    }

    @Override
    public void deleteById(String id) {
        userMongoRepository.deleteById(id);
    }

    private User mapTo(UserData userData) {
        return User.builder()
            .id(userData.getId())
            .name(userData.getName())
            .login(userData.getLogin())
            .password(userData.getPassword())
            .build();
    }

    private UserData mapFrom(User user) {
        UserData userData = new UserData();
        userData.setId(user.getId());
        userData.setName(user.getName());
        userData.setLogin(user.getLogin());
        userData.setPassword(user.getPassword());
        return userData;
    }
}
