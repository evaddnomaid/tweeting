package org.localomaha.reminders.tweeting.model;

import java.time.LocalDateTime;
import javax.validation.constraints.Size;


public class TweetHistoryDTO {

    private Long id;

    @Size(max = 255)
    private String message;

    private LocalDateTime whenSent;

    private Long tweetToHistory;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public LocalDateTime getWhenSent() {
        return whenSent;
    }

    public void setWhenSent(final LocalDateTime whenSent) {
        this.whenSent = whenSent;
    }

    public Long getTweetToHistory() {
        return tweetToHistory;
    }

    public void setTweetToHistory(final Long tweetToHistory) {
        this.tweetToHistory = tweetToHistory;
    }

}
