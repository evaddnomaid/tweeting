package org.localomaha.reminders.tweeting.domain;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;


@Entity
public class Tweets {

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
    private String crontab;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_tweets_id")
    private User userTweets;

    @OneToMany(mappedBy = "tweetToHistory")
    private Set<TweetHistory> tweetToHistoryTweetHistorys;

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

    public String getCrontab() {
        return crontab;
    }

    public void setCrontab(final String crontab) {
        this.crontab = crontab;
    }

    public User getUserTweets() {
        return userTweets;
    }

    public void setUserTweets(final User userTweets) {
        this.userTweets = userTweets;
    }

    public Set<TweetHistory> getTweetToHistoryTweetHistorys() {
        return tweetToHistoryTweetHistorys;
    }

    public void setTweetToHistoryTweetHistorys(
            final Set<TweetHistory> tweetToHistoryTweetHistorys) {
        this.tweetToHistoryTweetHistorys = tweetToHistoryTweetHistorys;
    }

}
