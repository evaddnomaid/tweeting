package org.localomaha.reminders.tweeting;

import org.localomaha.reminders.tweeting.model.TweetHistoryDTO;
import org.localomaha.reminders.tweeting.model.TweetsDTO;
import org.localomaha.reminders.tweeting.model.UserDTO;
import org.localomaha.reminders.tweeting.service.TweetHistoryService;
import org.localomaha.reminders.tweeting.service.TweetRunnable;
import org.localomaha.reminders.tweeting.service.TweetsService;
import org.localomaha.reminders.tweeting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.task.TaskSchedulerBuilder;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


@SpringBootApplication
public class TweetingApplication implements CommandLineRunner {

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
        TweetsDTO tweet1 = makeTweetSchedule("Hello world!","TODO: Crontab", dave, 2L);
        id = tweetsService.create(tweet1);
        tweet1.setId(id);
        TweetHistoryDTO tweetSent = makeSentTweet(tweet1);
        tweetHistoryService.create(tweetSent);
        for (int i=0; i < 5; i++) {
            tweetSent = makeSentTweet(tweet1);
            tweetHistoryService.create(tweetSent);
        }
        // TODO: Just read the database and schedule each
        // Every two seconds execute the run method of this class...
        TweetRunnable tweetRunnable = new TweetRunnable("this is the message every 50 seconds");
        tweetRunnable.setNumberTimes(3);
        // taskScheduler.schedule(tweetRunnable, new PeriodicTrigger(2, TimeUnit.MINUTES));

        // TODO: Get references to how to decode (and build) cron trigger expressions
        // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/scheduling/support/CronSequenceGenerator.html
        // "*/50 * * * * *" = every 50 seconds.
        ScheduledFuture<?> future = taskScheduler.schedule(tweetRunnable, new CronTrigger("*/50 * * * * *"));
        tweetRunnable.setFuture(future);
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
       return makeTweetSchedule(message, crontab, dave, 1L);
    }

    public static TweetsDTO makeTweetSchedule(String message, String crontab, UserDTO dave, Long numberToSend) {
        TweetsDTO t = new TweetsDTO();
        t.setMessage(message);
        t.setCrontab(crontab);
        t.setUserTweets(dave.getId());
        t.setNumberToSend(numberToSend);
        return t;
    }

    private UserDTO makeUser(String twitterHandle, String name, String email) {
        UserDTO u = new UserDTO();
        u.setTwitterHandle(twitterHandle);
        u.setName(name);
        u.setEmail(email);
        return u;
    }

}
