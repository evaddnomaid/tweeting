package org.localomaha.reminders.tweeting.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;


@Entity
public class TweetHistory {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column
    private String message;

    @Column
    private LocalDateTime whenSent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tweet_to_history_id")
    private Tweets tweetToHistory;

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

    public Tweets getTweetToHistory() {
        return tweetToHistory;
    }

    public void setTweetToHistory(final Tweets tweetToHistory) {
        this.tweetToHistory = tweetToHistory;
    }

}
