package com.capstone.users.infrastructure.drivenadapter;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The UserMySQLRepository interface provides the necessary methods to perform CRUD operations on UserData entities.
 * <p>
 * It extends JpaRepository, giving access to standard JPA repository methods such as save(), findById(), findAll(), and delete().
 * This interface is annotated with @Repository, making it a Spring Data component that can be injected into other classes.
 * <p>
 * JpaRepository<UserData, String>:
 * - UserData: The entity class being managed by this repository.
 * - String: The type of the entity's primary key.
 */
@Repository
public interface UserMongoRepository extends MongoRepository<UserData, String> {
    Optional<UserData> findByLogin(String login);
}
