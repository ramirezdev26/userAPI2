package com.capstone.users.domain.service;

import com.capstone.users.domain.model.User;
import com.capstone.users.domain.model.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByUsernameReturnsUserWhenUserExists() {
        /*User user = User.builder().id(UUID.randomUUID().toString()).username("testuser").build();
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByUsername("testuser");

        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());*/
    }

    @Test
    void findByUsernameReturnsEmptyWhenUserDoesNotExist() {
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());

        Optional<User> result = userService.findByUsername("nonexistentuser");

        assertFalse(result.isPresent());
    }

    @Test
    void saveCreatesNewUserWithUniqueId() {
        /*User newUser = User.builder().username("newuser").build();
        User savedUser = newUser.toBuilder().id(UUID.randomUUID().toString()).build();
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.save(newUser);

        assertNotNull(result.getId());
        assertEquals("newuser", result.getUsername());*/
    }
}