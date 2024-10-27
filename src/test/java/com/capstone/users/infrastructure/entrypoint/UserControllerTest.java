package com.capstone.users.infrastructure.entrypoint;

import com.capstone.users.domain.model.User;
import com.capstone.users.infrastructure.entrypoint.auth.dto.AuthToken;
import com.capstone.users.infrastructure.entrypoint.auth.dto.LoginResquest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("integration")
class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private User testUser;
    private String authToken;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id("6708969a6b07f64a6d295795")
                .name("Test User")
                .login("moralesann")
                .password("$_LoUgCM4!")
                .build();

        authToken = getAuthToken();
    }

    private String getAuthToken() {
        LoginResquest loginRequest = new LoginResquest("moralesann", "$_LoUgCM4!");
        ResponseEntity<AuthToken> response = restTemplate.postForEntity("/api/v1/users/auth/login", new HttpEntity<>(loginRequest), AuthToken.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        return response.getBody().getToken();
    }

    private HttpHeaders createHeadersWithAuthToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return headers;
    }

    @Test
    void testUpdateUser_Min() {
        User user = testUser.toBuilder().name("A").build();
        ResponseEntity<User> response = restTemplate.exchange("/api/v1/users/update/" + testUser.getId(), HttpMethod.PUT, new HttpEntity<>(user, createHeadersWithAuthToken()), User.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody().getName()).isEqualTo("A");
    }

    @Test
    void testUpdateUser_Max() {
        String longName = "A".repeat(255);
        User user = testUser.toBuilder().name(longName).build();
        ResponseEntity<User> response = restTemplate.exchange("/api/v1/users/update/" + testUser.getId(), HttpMethod.PUT, new HttpEntity<>(user, createHeadersWithAuthToken()), User.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody().getName()).isEqualTo(longName);
    }

    @Test
    void testUpdateUser_Avg() {
        String avgName = "Average User";
        User user = testUser.toBuilder().name(avgName).build();
        ResponseEntity<User> response = restTemplate.exchange("/api/v1/users/update/" + testUser.getId(), HttpMethod.PUT, new HttpEntity<>(user, createHeadersWithAuthToken()), User.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody().getName()).isEqualTo(avgName);
    }

    @Test
    void testGetUserById_Min() {
        ResponseEntity<User> response = restTemplate.exchange("/api/v1/users/get/" + testUser.getId(), HttpMethod.GET, new HttpEntity<>(createHeadersWithAuthToken()), User.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody().getId()).isEqualTo(testUser.getId());
    }

    @Test
    void testGetUserById_Max() {
        ResponseEntity<User> response = restTemplate.exchange("/api/v1/users/get/" + testUser.getId(), HttpMethod.GET, new HttpEntity<>(createHeadersWithAuthToken()), User.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody().getId()).isEqualTo(testUser.getId());
    }

    @Test
    void testGetUsers_Min() {
        ResponseEntity<List> response = restTemplate.exchange("/api/v1/users/get", HttpMethod.GET, new HttpEntity<>(createHeadersWithAuthToken()), List.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody().size()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void testGetUsers_Max() {
        ResponseEntity<List> response = restTemplate.exchange("/api/v1/users/get", HttpMethod.GET, new HttpEntity<>(createHeadersWithAuthToken()), List.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody().size()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void testGetUsers_Avg() {
        ResponseEntity<List> response = restTemplate.exchange("/api/v1/users/get", HttpMethod.GET, new HttpEntity<>(createHeadersWithAuthToken()), List.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody().size()).isGreaterThanOrEqualTo(0);
    }
}