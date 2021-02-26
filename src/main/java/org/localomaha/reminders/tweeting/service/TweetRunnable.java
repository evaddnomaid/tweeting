package org.localomaha.reminders.tweeting.service;

import java.util.concurrent.ScheduledFuture;

public class TweetRunnable implements Runnable {
    private final String message;
    private ScheduledFuture<?> future;
    private int numberTimesLeft;

    public TweetRunnable() {
        this.message = "default message";
    }

    public TweetRunnable(String m) {
        this.message = m;
    }

    @Override
    public void run() {
        System.out.println(this.message + " " + new java.util.Date());
        this.numberTimesLeft--;
        System.out.println(this.numberTimesLeft+ " tweets left!");
        if (this.numberTimesLeft <= 0) {
            System.out.println("Cancelling!");
            this.future.cancel(false);
        }
    }

    public void setFuture(ScheduledFuture<?> future) {
        this.future = future;
    }

    public void setNumberTimes(int i) {
        this.numberTimesLeft = i;
    }
}
