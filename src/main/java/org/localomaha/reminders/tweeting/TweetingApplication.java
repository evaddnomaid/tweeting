package org.localomaha.reminders.tweeting;

import org.localomaha.reminders.tweeting.model.TweetHistoryDTO;
import org.localomaha.reminders.tweeting.model.TweetsDTO;
import org.localomaha.reminders.tweeting.model.UserDTO;
import org.localomaha.reminders.tweeting.service.TweetHistoryService;
import org.localomaha.reminders.tweeting.service.TweetsService;
import org.localomaha.reminders.tweeting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.task.TaskSchedulerBuilder;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;


@SpringBootApplication
public class TweetingApplication implements CommandLineRunner, Runnable {

    @Autowired
    UserService userService;

    @Autowired
    TweetsService tweetsService;

    @Autowired
    TweetHistoryService tweetHistoryService;

    @Autowired
    private TaskScheduler taskScheduler;

    public static void main(String[] args) {
        SpringApplication.run(TweetingApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        UserDTO dave = makeUser("evaddnomaid","Dave Burchell","dave@dave.com");
        Long id = userService.create(dave);
        dave.setId(id);
        TweetsDTO tweet1 = makeTweetSchedule("Hello world!","TODO: Crontab", dave);
        id = tweetsService.create(tweet1);
        tweet1.setId(id);
        TweetHistoryDTO tweetSent = makeSentTweet(tweet1);
        tweetHistoryService.create(tweetSent);
        for (int i=0; i < 5; i++) {
            tweetSent = makeSentTweet(tweet1);
            tweetHistoryService.create(tweetSent);
        }
        taskScheduler.schedule(this, new PeriodicTrigger(2, TimeUnit.SECONDS));
    }

    public static TweetHistoryDTO makeSentTweet(TweetsDTO tweet1) {
        TweetHistoryDTO tweetSent=new TweetHistoryDTO();
        tweetSent.setMessage(tweet1.getMessage());
        tweetSent.setTweetToHistory(tweet1.getId());
        LocalDateTime currentTime=LocalDateTime.now();
        tweetSent.setWhenSent(currentTime);
        return tweetSent;
    }

    public static TweetsDTO makeTweetSchedule(String message, String crontab, UserDTO dave) {
        TweetsDTO t = new TweetsDTO();
        t.setMessage(message);
        t.setCrontab(crontab);
        t.setUserTweets(dave.getId());
        return t;
    }

    private UserDTO makeUser(String twitterHandle, String name, String email) {
        UserDTO u = new UserDTO();
        u.setTwitterHandle(twitterHandle);
        u.setName(name);
        u.setEmail(email);
        return u;
    }

    @Override
    public void run() {
        System.out.println("run! " + new java.util.Date());
    }
}
