package com.capstone.users.domain.service;

import com.capstone.users.domain.exceptions.CustomersNotFoundException;
import com.capstone.users.domain.exceptions.InvalidUserDataException;
import com.capstone.users.domain.exceptions.UserAlreadyExistsException;
import com.capstone.users.domain.exceptions.UserNotFound;
import com.capstone.users.domain.model.User;
import com.capstone.users.domain.model.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link UserService} class.
 * <p>
 * This class contains test methods to validate the behavior of the UserService class.
 * It uses Mockito to mock the dependencies and verify interactions and behaviors.
 * The tests cover methods for finding a user by login and saving a user, including scenarios
 * for when a user already exists.
 */
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @BeforeEach
    void setup()
    {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the behavior of {@link UserService#findByLogin(String)} when the user exists.
     * <p>
     * This test ensures that when a user with the specified login exists in the repository,
     * the method returns an Optional containing the User object.
     */
    @Test
    void TestFindByLogin_WhenUserExists_ShouldReturnAnUser() {
        String login = "testUser";
        User user = User.builder().login(login).build();
        when(userRepository.findByLogin(login)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByLogin(login);

        assertTrue(result.isPresent());
        assertEquals(login, result.get().getLogin());
        verify(userRepository, times(1)).findByLogin(login);
    }

    /**
     * Tests the behavior of {@link UserService#findByLogin(String)} when the user does not exist.
     * <p>
     * This test ensures that when no user with the specified login exists in the repository,
     * the method returns an empty Optional.
     */
    @Test
    void TestFindByLogin_WhenUserDoesNotExist_ShouldReturnAnEmptyOptional() {
        String login = "testUser";
        when(userRepository.findByLogin(login)).thenReturn(Optional.empty());

        Optional<User> result = userService.findByLogin(login);

        assertEquals(Optional.empty(), result);
    }

    /**
     * Tests the behavior of {@link UserService#save(User)} when the user login already exists.
     * <p>
     * This test ensures that when trying to save a user with a login that already exists in the repository,
     * a {@link UserAlreadyExistsException} is thrown.
     */
    @Test
    void TestSaveUser_WhenLoginExists_ShouldReturnAnException() {
        String name = "testName";
        String existingLogin = "testUser";
        String password = "testPassword";
        User existingUser = User.builder().login(existingLogin).name(name).password(password).build();
        when(userRepository.findByLogin(existingLogin)).thenReturn(Optional.of(existingUser));

        User userToRegister = User.builder().login(existingLogin).name("name").password("password").build();

        assertThrows(UserAlreadyExistsException.class, () -> userService.save(userToRegister));
    }

    /**
     * Tests the behavior of {@link UserService#save(User)} when the user login does not exist.
     * <p>
     * This test ensures that when saving a user with a unique login, the user is saved successfully
     * and no exceptions are thrown. It verifies that the userRepository's save method is called with
     * the correct data.
     */
    @Test
    void TestSaveUser_WhenLoginDoesNotExist_ShouldSaveUserSuccessfully()
    {
        String name = "testName";
        String login = "testUser";
        String password = "testPassword";
        User user = User.builder().login(login).name(name).password(password).build();

        when(userRepository.findByLogin(login)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.save(user);

        verify(userRepository, times(1)).save(result);
        assertEquals(login, result.getLogin());
        assertEquals(name, result.getName());
        assertEquals(password, result.getPassword());
    }

    /**
     * Tests the behavior of {@link UserService#update(String, User)} when user fields are empty.
     * <p>
     * Ensures that updating a user with empty fields triggers an {@link InvalidUserDataException}.
     */
    @Test
    void TestUpdateUser_WhenFieldsAreEmpty_ShouldThrowInvalidUserDataException() {
        String id = "userId";
        User existingUser = User.builder().id(id).login("testUser").name("testName").password("testPassword").build();
        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));

        User userToUpdate = User.builder().id(id).login("").name("").password("").build();

        assertThrows(InvalidUserDataException.class, () -> userService.update(id, userToUpdate));
    }

    /**
     * Tests the behavior of {@link UserService#update(String, User)} when the user does not exist.
     * <p>
     * Ensures that trying to update a non-existent user triggers a {@link CustomersNotFoundException}.
     */
    @Test
    void TestUpdateUser_WhenUserDoesNotExist_ShouldThrowCustomersNotFoundException() {
        String id = "userId";
        User userToUpdate = User.builder().id(id).login("testUser").name("testName").password("testPassword").build();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFound.class, () -> userService.update(id, userToUpdate));
    }

    /**
     * Tests the behavior of {@link UserService#update(String, User)} when a user exists.
     * <p>
     * Ensures that the user is updated successfully with new data,
     * and verifies that the repository's update method is called correctly.
     */
    @Test
    void TestUpdateUser_WhenUserExists_ShouldUpdateUserSuccessfully() {

        String id = "userId";
        String oldName = "oldName";
        String oldLogin = "oldLogin";
        String oldPassword = "oldPassword";

        User existingUser = User.builder().id(id).login(oldLogin).name(oldName).password(oldPassword).build();

        String newName = "newName";
        String newLogin = "newLogin";
        String newPassword = "newPassword";

        User updatedUser = User.builder().id(id).login(newLogin).name(newName).password(newPassword).build();

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRepository.findByLogin(newLogin)).thenReturn(Optional.empty());
        when(userRepository.update(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.update(id, updatedUser);

        verify(userRepository, times(1)).update(result);

        assertEquals(newLogin, result.getLogin());
        assertEquals(newName, result.getName());
        assertEquals(newPassword, result.getPassword());
    }
}