package org.localomaha.reminders.tweeting.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.localomaha.reminders.tweeting.config.BaseIT;
import org.localomaha.reminders.tweeting.model.ErrorResponse;
import org.localomaha.reminders.tweeting.model.TweetHistoryDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;


public class TweetHistoryControllerTest extends BaseIT {

    @Test
    @Sql("/data/tweetHistoryData.sql")
    public void getAllTweetHistorys_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<List<TweetHistoryDTO>> response = restTemplate.exchange(
                "/api/tweetHistorys", HttpMethod.GET, request, new ParameterizedTypeReference<List<TweetHistoryDTO>>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals((long)1200, response.getBody().get(0).getId());
    }

    @Test
    @Sql("/data/tweetHistoryData.sql")
    public void getTweetHistory_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<TweetHistoryDTO> response = restTemplate.exchange(
                "/api/tweetHistorys/1200", HttpMethod.GET, request, TweetHistoryDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cras sed interdum...", response.getBody().getMessage());
    }

    @Test
    public void getTweetHistory_notFound() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                "/api/tweetHistorys/1866", HttpMethod.GET, request, ErrorResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("ResponseStatusException", response.getBody().getException());
    }

    @Test
    public void createTweetHistory_success() {
        final HttpEntity<String> request = new HttpEntity<>(readResource("/requests/tweetHistoryDTORequest.json"), headers());
        final ResponseEntity<Long> response = restTemplate.exchange(
                "/api/tweetHistorys", HttpMethod.POST, request, Long.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, tweetHistoryRepository.count());
    }

    @Test
    @Sql("/data/tweetHistoryData.sql")
    public void updateTweetHistory_success() {
        final HttpEntity<String> request = new HttpEntity<>(readResource("/requests/tweetHistoryDTORequest.json"), headers());
        final ResponseEntity<Void> response = restTemplate.exchange(
                "/api/tweetHistorys/1200", HttpMethod.PUT, request, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Donec ac nibh...", tweetHistoryRepository.findById((long)1200).get().getMessage());
    }

    @Test
    @Sql("/data/tweetHistoryData.sql")
    public void deleteTweetHistory_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<Void> response = restTemplate.exchange(
                "/api/tweetHistorys/1200", HttpMethod.DELETE, request, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(0, tweetHistoryRepository.count());
    }

}
