package org.localomaha.reminders.tweeting.rest;

import java.util.List;
import javax.validation.Valid;
import org.localomaha.reminders.tweeting.model.TweetHistoryDTO;
import org.localomaha.reminders.tweeting.service.TweetHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/tweetHistorys", produces = MediaType.APPLICATION_JSON_VALUE)
public class TweetHistoryController {

    private final TweetHistoryService tweetHistoryService;

    public TweetHistoryController(final TweetHistoryService tweetHistoryService) {
        this.tweetHistoryService = tweetHistoryService;
    }

    @GetMapping
    public ResponseEntity<List<TweetHistoryDTO>> getAllTweetHistorys() {
        return ResponseEntity.ok(tweetHistoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TweetHistoryDTO> getTweetHistory(@PathVariable final Long id) {
        return ResponseEntity.ok(tweetHistoryService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createTweetHistory(
            @RequestBody @Valid final TweetHistoryDTO tweetHistoryDTO) {
        return new ResponseEntity<>(tweetHistoryService.create(tweetHistoryDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTweetHistory(@PathVariable final Long id,
            @RequestBody @Valid final TweetHistoryDTO tweetHistoryDTO) {
        tweetHistoryService.update(id, tweetHistoryDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTweetHistory(@PathVariable final Long id) {
        tweetHistoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
