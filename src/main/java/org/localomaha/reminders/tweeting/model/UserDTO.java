package org.localomaha.reminders.tweeting.model;

import javax.validation.constraints.Size;


public class UserDTO {

    private Long id;

    @Size(max = 255)
    private String twitterHandle;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTwitterHandle() {
        return twitterHandle;
    }

    public void setTwitterHandle(final String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

}
