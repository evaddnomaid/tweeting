package org.localomaha.reminders.tweeting.service;

public class TweetRunnable implements Runnable {
    private final String message;

    public TweetRunnable() {
        this.message = "default message";
    }

    public TweetRunnable(String m) {
        this.message = m;
    }

    @Override
    public void run() {
        System.out.println(this.message + " " + new java.util.Date());
    }
}
