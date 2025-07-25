package com.capstone.users.domain.model;

import java.util.List;
import java.util.Optional;

/**
 * The UserRepository interface defines the contract for how user-related operations will be handled in the persistence layer.
 * It abstracts the data access logic from the domain, ensuring that the domain remains framework and infrastructure-agnostic.
 */
public interface UserRepository {
    User save(User user);
    Optional<User> findByLogin(String login);
    User update(User user);
    Optional<User> findById(String id);
    List<User> findAll();
    void deleteById(String id);
}
