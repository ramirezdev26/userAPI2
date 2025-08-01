package com.capstone.users.infrastructure.drivenadapter;

import jakarta.persistence.*;
import lombok.Data;

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
@Data
@Entity
@Table(name="users", uniqueConstraints = {@UniqueConstraint(columnNames = {"login"})})
public class UserData {
    @Id
    private String id;
    @Column
    private String name;
    @Column
    private String login;
    @Column
    private String password;
}
