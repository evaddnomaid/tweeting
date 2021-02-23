package org.localomaha.reminders.tweeting.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.localomaha.reminders.tweeting.config.BaseIT;
import org.localomaha.reminders.tweeting.model.ErrorResponse;
import org.localomaha.reminders.tweeting.model.TweetsDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;


public class TweetsControllerTest extends BaseIT {

    @Test
    @Sql("/data/tweetsData.sql")
    public void getAllTweetss_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<List<TweetsDTO>> response = restTemplate.exchange(
                "/api/tweetss", HttpMethod.GET, request, new ParameterizedTypeReference<List<TweetsDTO>>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals((long)1100, response.getBody().get(0).getId());
    }

    @Test
    @Sql("/data/tweetsData.sql")
    public void getTweets_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<TweetsDTO> response = restTemplate.exchange(
                "/api/tweetss/1100", HttpMethod.GET, request, TweetsDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cras sed interdum...", response.getBody().getMessage());
    }

    @Test
    public void getTweets_notFound() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                "/api/tweetss/1766", HttpMethod.GET, request, ErrorResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("ResponseStatusException", response.getBody().getException());
    }

    @Test
    public void createTweets_success() {
        final HttpEntity<String> request = new HttpEntity<>(readResource("/requests/tweetsDTORequest.json"), headers());
        final ResponseEntity<Long> response = restTemplate.exchange(
                "/api/tweetss", HttpMethod.POST, request, Long.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, tweetsRepository.count());
    }

    @Test
    @Sql("/data/tweetsData.sql")
    public void updateTweets_success() {
        final HttpEntity<String> request = new HttpEntity<>(readResource("/requests/tweetsDTORequest.json"), headers());
        final ResponseEntity<Void> response = restTemplate.exchange(
                "/api/tweetss/1100", HttpMethod.PUT, request, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Donec ac nibh...", tweetsRepository.findById((long)1100).get().getMessage());
    }

    @Test
    @Sql("/data/tweetsData.sql")
    public void deleteTweets_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<Void> response = restTemplate.exchange(
                "/api/tweetss/1100", HttpMethod.DELETE, request, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(0, tweetsRepository.count());
    }

}
