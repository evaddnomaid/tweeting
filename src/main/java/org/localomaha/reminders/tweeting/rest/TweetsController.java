package org.localomaha.reminders.tweeting.rest;

import java.util.List;
import javax.validation.Valid;
import org.localomaha.reminders.tweeting.model.TweetsDTO;
import org.localomaha.reminders.tweeting.service.TweetsService;
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
@RequestMapping(value = "/api/tweetss", produces = MediaType.APPLICATION_JSON_VALUE)
public class TweetsController {

    private final TweetsService tweetsService;

    public TweetsController(final TweetsService tweetsService) {
        this.tweetsService = tweetsService;
    }

    @GetMapping
    public ResponseEntity<List<TweetsDTO>> getAllTweetss() {
        return ResponseEntity.ok(tweetsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TweetsDTO> getTweets(@PathVariable final Long id) {
        return ResponseEntity.ok(tweetsService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createTweets(@RequestBody @Valid final TweetsDTO tweetsDTO) {
        return new ResponseEntity<>(tweetsService.create(tweetsDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTweets(@PathVariable final Long id,
            @RequestBody @Valid final TweetsDTO tweetsDTO) {
        tweetsService.update(id, tweetsDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTweets(@PathVariable final Long id) {
        tweetsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
