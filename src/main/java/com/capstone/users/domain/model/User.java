package com.capstone.users.domain.model;

import com.capstone.users.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * The User class represents the core user entity in the domain.
 * It encapsulates user-related data and provides methods for manipulating this data.
 * <p>
 * Fields:
 * - id: Unique identifier for the user.
 * - name: Name of the user.
 * - login: User's login credential.
 * - password: User's password (this should be stored securely and hashed in practice).
 * <p>
 * Methods:
 * - cloneFrom(User refUser): Creates a clone of the current user object from a reference user.
 *   If certain fields (id, name, login, password) are missing from the current user, they are replaced
 *   with the corresponding values from the reference user.
 */
@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private String id;
    private String name;
    private String login;

    private String password;

    public User cloneFrom(User refUser) {
        User clonedUser = new User(id, name, login, password);
        if (StringUtils.isNullOrEmpty(clonedUser.getId())) {
            clonedUser.setId(refUser.getId());
        }

        if (StringUtils.isNullOrEmpty(clonedUser.getLogin())) {
            clonedUser.setLogin(refUser.getLogin());
        }

        if (StringUtils.isNullOrEmpty(clonedUser.getName())) {
            clonedUser.setName(refUser.getName());
        }

        if (StringUtils.isNullOrEmpty(clonedUser.getPassword())) {
            clonedUser.setPassword(refUser.getPassword());
        }
        return clonedUser;
    }
}
