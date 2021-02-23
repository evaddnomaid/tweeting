package org.localomaha.reminders.tweeting.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.localomaha.reminders.tweeting.config.BaseIT;
import org.localomaha.reminders.tweeting.model.ErrorResponse;
import org.localomaha.reminders.tweeting.model.UserDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;


public class UserControllerTest extends BaseIT {

    @Test
    @Sql("/data/userData.sql")
    public void getAllUsers_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<List<UserDTO>> response = restTemplate.exchange(
                "/api/users", HttpMethod.GET, request, new ParameterizedTypeReference<List<UserDTO>>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals((long)1000, response.getBody().get(0).getId());
    }

    @Test
    @Sql("/data/userData.sql")
    public void getUser_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<UserDTO> response = restTemplate.exchange(
                "/api/users/1000", HttpMethod.GET, request, UserDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Aenean pulvinar...", response.getBody().getTwitterHandle());
    }

    @Test
    public void getUser_notFound() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                "/api/users/1666", HttpMethod.GET, request, ErrorResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("ResponseStatusException", response.getBody().getException());
    }

    @Test
    public void createUser_success() {
        final HttpEntity<String> request = new HttpEntity<>(readResource("/requests/userDTORequest.json"), headers());
        final ResponseEntity<Long> response = restTemplate.exchange(
                "/api/users", HttpMethod.POST, request, Long.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, userRepository.count());
    }

    @Test
    @Sql("/data/userData.sql")
    public void updateUser_success() {
        final HttpEntity<String> request = new HttpEntity<>(readResource("/requests/userDTORequest.json"), headers());
        final ResponseEntity<Void> response = restTemplate.exchange(
                "/api/users/1000", HttpMethod.PUT, request, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ut pellentesque sapien...", userRepository.findById((long)1000).get().getTwitterHandle());
    }

    @Test
    @Sql("/data/userData.sql")
    public void deleteUser_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<Void> response = restTemplate.exchange(
                "/api/users/1000", HttpMethod.DELETE, request, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(0, userRepository.count());
    }

}
