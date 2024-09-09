package com.capstone.users.infrastructure.drivenadapter;

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

    @Override
    public Optional<User> findByUsername(String username) {
        return userMySQLRepository.findByLogin(username)
                .map(UserData::toUser)
                .map(Optional::of)
                .orElse(null);
    }

    @Override
    public User save(User newUser) {
        return userMySQLRepository.save(UserData.fromUser(newUser)).toUser();
    }
}
