package com.capstone.users.infrastructure.drivenadapter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

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
public class UserRepositoryAdapter {
}
