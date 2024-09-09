package com.capstone.users.domain.model;

import java.util.Optional;

/**
 * The UserRepository interface defines the contract for how user-related operations will be handled in the persistence layer.
 * It abstracts the data access logic from the domain, ensuring that the domain remains framework and infrastructure-agnostic.
 */
public interface UserRepository {
    Optional<User> findByUsername(String username);
    User save(User newUser);
}
