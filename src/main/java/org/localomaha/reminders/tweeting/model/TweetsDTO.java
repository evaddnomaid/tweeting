package org.localomaha.reminders.tweeting.model;

import javax.validation.constraints.Size;


public class TweetsDTO {

    private Long id;

    @Size(max = 255)
    private String message;

    @Size(max = 255)
    private String crontab;

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

}
