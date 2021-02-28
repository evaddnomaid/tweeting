package org.localomaha.reminders.tweeting.model;

import javax.validation.constraints.Size;


public class TweetsDTO {

    private Long id;

    @Size(max = 255)
    private String message;

    @Size(max = 255)
    private String crontab;

    private Long numberToSend;

    private Long userTweets;

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

    public Long getNumberToSend() {
        return numberToSend;
    }

    public void setNumberToSend(Long numberToSend) {
        this.numberToSend = numberToSend;
    }

    public Long getUserTweets() {
        return userTweets;
    }

    public void setUserTweets(final Long userTweets) {
        this.userTweets = userTweets;
    }

    @Override
    public String toString() {
        return "TweetsDTO{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", crontab='" + crontab + '\'' +
                ", numberToSend=" + numberToSend +
                ", userTweets=" + userTweets +
                '}';
    }
}
