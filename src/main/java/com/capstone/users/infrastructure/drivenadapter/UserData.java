package com.capstone.users.infrastructure.drivenadapter;

import com.capstone.users.domain.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * The UserData class represents the database entity for user information.
 * It is mapped to the "users" table in the database and includes fields like id, name, login, and password.
 * <p>
 * This class is annotated with @Entity and @Table, marking it as a JPA entity. Each field is mapped
 * to a column in the "users" table through the @Column annotation.
 * <p>
 * Fields:
 * - id: The unique identifier for the user, marked as the primary key with @Id.
 * - name: The name of the user.
 * - login: The login credential for the user.
 * - password: The user's password (stored in plain text here but should ideally be hashed and salted).
 */
@Entity
@Table(name="users")
public class UserData {
    @Id
    private String id;
    @Column
    private String name;
    @Column
    private String login;
    @Column
    private String password;

    /**
     * Converts the UserData entity into a User domain model object.
     *
     * @return A User domain model object with the data from this entity.
     */
    public User toUser() {
        return new User(id, name, login, password);
    }
}
