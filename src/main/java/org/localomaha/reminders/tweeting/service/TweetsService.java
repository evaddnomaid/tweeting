package org.localomaha.reminders.tweeting.service;

import java.util.List;
import java.util.stream.Collectors;

import org.localomaha.reminders.tweeting.TweetingApplication;
import org.localomaha.reminders.tweeting.domain.Tweets;
import org.localomaha.reminders.tweeting.domain.User;
import org.localomaha.reminders.tweeting.model.TweetHistoryDTO;
import org.localomaha.reminders.tweeting.model.TweetsDTO;
import org.localomaha.reminders.tweeting.model.UserDTO;
import org.localomaha.reminders.tweeting.repos.TweetsRepository;
import org.localomaha.reminders.tweeting.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class TweetsService {

    private final TweetsRepository tweetsRepository;
    private final UserRepository userRepository;

    @Autowired
    private TweetHistoryService tweetHistoryService;

    @Autowired
    private UserService userService;

    public TweetsService(final TweetsRepository tweetsRepository,
            final UserRepository userRepository) {
        this.tweetsRepository = tweetsRepository;
        this.userRepository = userRepository;
    }

    // @Scheduled(cron = "0 0/1 * * * *")
//    @Scheduled(cron = "*/1 * * * *")
//    public void repeater() {
//        System.out.println("repeater! " + new java.util.Date());
//
//        UserDTO dave = userService.get(10_000L);
//        TweetsDTO tweet1 = TweetingApplication.makeTweetSchedule("Hello world!","TODO: Crontab", dave);
//        Long id = create(tweet1);
//        tweet1.setId(id);
//        TweetHistoryDTO tweetSent = TweetingApplication.makeSentTweet(tweet1);
//        tweetHistoryService.create(tweetSent);
//    }

    public List<TweetsDTO> findAll() {
        return tweetsRepository.findAll()
                .stream()
                .map(tweets -> mapToDTO(tweets, new TweetsDTO()))
                .collect(Collectors.toList());
    }

    public TweetsDTO get(final Long id) {
        return tweetsRepository.findById(id)
                .map(tweets -> mapToDTO(tweets, new TweetsDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final TweetsDTO tweetsDTO) {
        final Tweets tweets = new Tweets();
        mapToEntity(tweetsDTO, tweets);
        return tweetsRepository.save(tweets).getId();
    }

    public void update(final Long id, final TweetsDTO tweetsDTO) {
        final Tweets tweets = tweetsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(tweetsDTO, tweets);
        tweetsRepository.save(tweets);
    }

    public void delete(final Long id) {
        tweetsRepository.deleteById(id);
    }

    private TweetsDTO mapToDTO(final Tweets tweets, final TweetsDTO tweetsDTO) {
        tweetsDTO.setId(tweets.getId());
        tweetsDTO.setMessage(tweets.getMessage());
        tweetsDTO.setCrontab(tweets.getCrontab());
        tweetsDTO.setUserTweets(tweets.getUserTweets() == null ? null : tweets.getUserTweets().getId());
        return tweetsDTO;
    }

    private Tweets mapToEntity(final TweetsDTO tweetsDTO, final Tweets tweets) {
        tweets.setMessage(tweetsDTO.getMessage());
        tweets.setCrontab(tweetsDTO.getCrontab());
        if (tweetsDTO.getUserTweets() != null && 
                (tweets.getUserTweets() == null || !tweets.getUserTweets().getId().equals(tweetsDTO.getUserTweets()))) {
            final User userTweets = userRepository.findById(tweetsDTO.getUserTweets())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "userTweets not found"));
            tweets.setUserTweets(userTweets);
        }
        return tweets;
    }

}
