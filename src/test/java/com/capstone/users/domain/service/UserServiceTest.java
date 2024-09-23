package com.capstone.users.domain.service;

import com.capstone.users.domain.exceptions.CustomersNotFoundException;
import com.capstone.users.domain.exceptions.userExceptions.UserAlreadyExistsException;
import com.capstone.users.domain.exceptions.userExceptions.UserEmptyDataException;
import com.capstone.users.domain.exceptions.userExceptions.UserNotFoundException;
import com.capstone.users.domain.model.User;
import com.capstone.users.domain.model.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
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

    @Test
    void TestSaveUser_WhenUserNameIsEmpty_ShouldThrowUserEmptyDataException() {
        String login = "testUser";
        String password = "testPassword";
        User user = User.builder().login(login).name("").password(password).build();

        assertThrows(UserEmptyDataException.class, () -> userService.save(user));

        verifyNoInteractions(userRepository);
    }

    @Test
    void TestSaveUser_WhenUserLoginIsEmpty_ShouldThrowUserEmptyDataException() {
        String name = "testName";
        String password = "testPassword";
        User user = User.builder().login("").name(name).password(password).build();

        assertThrows(UserEmptyDataException.class, () -> userService.save(user));

        verifyNoInteractions(userRepository);
    }

    @Test
    void TestSaveUser_WhenUserPasswordIsEmpty_ShouldThrowUserEmptyDataException() {
        String name = "testName";
        String login = "testUser";
        User user = User.builder().login(login).name(name).password("").build();

        assertThrows(UserEmptyDataException.class, () -> userService.save(user));

        verifyNoInteractions(userRepository);
    }

    @Test
    void TestSaveUser_WhenUserDataAreEmpty_ShouldThrowUserEmptyDataException() {
        User user = User.builder().login("").name("").password("").build();

        assertThrows(UserEmptyDataException.class, () -> userService.save(user));

        verifyNoInteractions(userRepository);
    }

    /**
     * Tests the behavior of {@link UserService#update(String, User)} when user fields are empty.
     * <p>
     * Ensures that updating a user with empty fields triggers an {@link UserEmptyDataException}.
     */
    @Test
    void TestUpdateUser_WhenFieldsAreEmpty_ShouldThrowInvalidUserDataException() {
        String id = "userId";
        User existingUser = User.builder().id(id).login("testUser").name("testName").password("testPassword").build();
        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));

        User userToUpdate = User.builder().id(id).login("").name("").password("").build();

        assertThrows(UserEmptyDataException.class, () -> userService.update(id, userToUpdate));
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

        assertThrows(UserNotFoundException.class, () -> userService.update(id, userToUpdate));
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

    /**
     * Tests the behavior of {@link UserService#update(String, User)} when the login already exists.
     * <p>
     * Ensures that trying to update a user with a login that is already used by another user
     * triggers a {@link UserAlreadyExistsException}.
     */
    @Test
    void TestUpdateUser_WhenLoginAlreadyExists_ShouldThrowUserAlreadyExistsException() {
        String id = "userId";
        String existingLogin = "existingUser";
        User existingUser = User.builder().id(id).login("oldLogin").name("oldName").password("oldPassword").build();
        User userWithSameLogin = User.builder().id("anotherId").login(existingLogin).build();

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRepository.findByLogin(existingLogin)).thenReturn(Optional.of(userWithSameLogin));

        User userToUpdate = User.builder().id(id).login(existingLogin).name("newName").password("newPassword").build();

        assertThrows(UserAlreadyExistsException.class, () -> userService.update(id, userToUpdate));
    }

    /**
     * Tests the behavior of {@link UserService#update(String, User)} when the password is null.
     * <p>
     * Ensures that updating a user with a null password triggers an {@link UserEmptyDataException}.
     * The existing password should remain unchanged if the password is not provided.
     */
    @Test
    void TestUpdateUser_WhenPasswordIsNull_ShouldKeepExistingPassword() {
        String id = "userId";
        String oldLogin = "testUser";
        String oldPassword = "oldPassword";
        User existingUser = User.builder().id(id).login(oldLogin).name("oldName").password(oldPassword).build();

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));

        User userToUpdate = User.builder().id(id).login(oldLogin).name("newName").password(null).build();

        when(userRepository.update(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        assertThrows(UserEmptyDataException.class, () -> userService.update(id, userToUpdate));

    }

    /**
     * Tests the behavior of {@link UserService#findById(String)} when the user exists.
     * <p>
     * This test ensures that when a user with the specified ID exists in the repository,
     * the method returns the User object.
     */
    @Test
    void TestFindById_WhenUserExists_ShouldReturnUser() {
        String userId = "userId";
        User user = User.builder().id(userId).name("testName").login("testLogin").password("testPassword").build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.findById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(userRepository, times(1)).findById(userId);
    }

    /**
     * Tests the behavior of {@link UserService#findById(String)} when the user does not exist.
     * <p>
     * This test ensures that when no user with the specified ID exists in the repository,
     * a {@link UserNotFoundException} is thrown.
     */
    @Test
    void TestFindById_WhenUserDoesNotExist_ShouldThrowUserNotFoundException() {
        String userId = "userId";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    /**
     * Tests the behavior of {@link UserService#findAll()} when users exist.
     * <p>
     * This test ensures that when users are present in the repository,
     * the method returns a non-empty list of User objects.
     */
    @Test
    void TestFindAll_WhenUsersExist_ShouldReturnListOfUsers() {
        User user1 = User.builder().id("user1").name("testName1").login("testLogin1").password("testPassword1").build();
        User user2 = User.builder().id("user2").name("testName2").login("testLogin2").password("testPassword2").build();

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<User> result = userService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    /**
     * Tests the behavior of {@link UserService#findAll()} when no users exist.
     * <p>
     * This test ensures that when there are no users in the repository,
     * the method returns an empty list.
     */
    @Test
    void TestFindAll_WhenNoUsersExist_ShouldReturnEmptyList() {
        when(userRepository.findAll()).thenReturn(List.of());

        List<User> result = userService.findAll();

        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findAll();
    }


     /* Tests the behavior of {@link UserService#deleteById(String)} when the user exists.
     * <p>
     * Ensures that when a user with the given ID exists in the repository,
     * the method successfully deletes the user and returns a success message.
     * The success message should include the ID of the deleted user.
     */
    @Test
    void testDeleteById_WhenUserExists_ShouldDeleteSuccessfullyAndReturnMessage() {
      String userId = "testId";
      when(userRepository.findById(userId)).thenReturn(Optional.of(new User("1", "testUser", "testName", "testPassword")));

      String result = userService.deleteById(userId);

      assertEquals("User with ID: " + userId + " deleted successfully", result);
      verify(userRepository, times(1)).deleteById(userId);
    }

    /**
     * Tests the behavior of {@link UserService#deleteById(String)} when the user does not exist.
     * <p>
     * Ensures that when a user with the given ID does not exist in the repository,
     * the method throws a {@link UserNotFoundException} and does not attempt to delete the user.
     */
    @Test
    void testDeleteById_WhenUserDoesNotExist_ShouldThrowUserNotFoundException() {
      String userId = "nonExistentId";
      when(userRepository.findById(userId)).thenReturn(Optional.empty());

      assertThrows(UserNotFoundException.class, () -> userService.deleteById(userId));

      verify(userRepository, never()).deleteById(anyString());
    }

    /**
     * Tests the behavior of {@link UserService#deleteById(String)} when the id is null.
     * <p>
     * This test ensures that when the id is null, an IllegalArgumentException is thrown.
     * It verifies that the repository is not interacted with.
     */
    @Test
    void testDeleteById_WhenIdIsNull_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> userService.deleteById(null));

        verifyNoInteractions(userRepository);
    }

}