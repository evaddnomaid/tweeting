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
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;


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

    private void postTweet(String latestStatus) throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        Status status = twitter.updateStatus(latestStatus);
    }

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
