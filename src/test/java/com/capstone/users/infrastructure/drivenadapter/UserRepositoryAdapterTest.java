package com.capstone.users.infrastructure.drivenadapter;

import com.capstone.users.domain.model.User;
import com.capstone.users.domain.model.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Tag("integration")
class UserRepositoryAdapterTest {

    @Autowired
    private UserRepository userRepositoryAdapter;

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = User.builder()
                .id("1")
                .name("Test User")
                .login("testuser")
                .password("password")
                .build();
    }

    @Test
    void testDatabaseConnectivity() {
        assertThat(userRepositoryAdapter).isNotNull();
    }

    @Test
    void testSaveAndFindUser() {
        userRepositoryAdapter.save(testUser);
        Optional<User> foundUser = userRepositoryAdapter.findById(testUser.getId());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo(testUser.getName());
    }

    @Test
    void testUserStructure() {
        userRepositoryAdapter.save(testUser);
        Optional<User> foundUser = userRepositoryAdapter.findById(testUser.getId());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getId()).isEqualTo(testUser.getId());
        assertThat(foundUser.get().getName()).isEqualTo(testUser.getName());
        assertThat(foundUser.get().getLogin()).isEqualTo(testUser.getLogin());
        assertThat(foundUser.get().getPassword()).isEqualTo(testUser.getPassword());
    }

    @Test
    void testDataIntegrity() {
        userRepositoryAdapter.save(testUser);
        Optional<User> foundUser = userRepositoryAdapter.findById(testUser.getId());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo(testUser.getName());
        assertThat(foundUser.get().getLogin()).isEqualTo(testUser.getLogin());
        assertThat(foundUser.get().getPassword()).isEqualTo(testUser.getPassword());
    }

    @Test
    void testPerformance() {
        long startTime = System.currentTimeMillis();
        userRepositoryAdapter.save(testUser);
        long endTime = System.currentTimeMillis();
        assertThat(endTime - startTime).isLessThan(1000);
    }
}