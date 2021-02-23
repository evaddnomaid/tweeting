package org.localomaha.reminders.tweeting.service;

import java.util.List;
import java.util.stream.Collectors;
import org.localomaha.reminders.tweeting.domain.TweetHistory;
import org.localomaha.reminders.tweeting.domain.Tweets;
import org.localomaha.reminders.tweeting.model.TweetHistoryDTO;
import org.localomaha.reminders.tweeting.repos.TweetHistoryRepository;
import org.localomaha.reminders.tweeting.repos.TweetsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class TweetHistoryService {

    private final TweetHistoryRepository tweetHistoryRepository;
    private final TweetsRepository tweetsRepository;

    public TweetHistoryService(final TweetHistoryRepository tweetHistoryRepository,
            final TweetsRepository tweetsRepository) {
        this.tweetHistoryRepository = tweetHistoryRepository;
        this.tweetsRepository = tweetsRepository;
    }

    public List<TweetHistoryDTO> findAll() {
        return tweetHistoryRepository.findAll()
                .stream()
                .map(tweetHistory -> mapToDTO(tweetHistory, new TweetHistoryDTO()))
                .collect(Collectors.toList());
    }

    public TweetHistoryDTO get(final Long id) {
        return tweetHistoryRepository.findById(id)
                .map(tweetHistory -> mapToDTO(tweetHistory, new TweetHistoryDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final TweetHistoryDTO tweetHistoryDTO) {
        final TweetHistory tweetHistory = new TweetHistory();
        mapToEntity(tweetHistoryDTO, tweetHistory);
        return tweetHistoryRepository.save(tweetHistory).getId();
    }

    public void update(final Long id, final TweetHistoryDTO tweetHistoryDTO) {
        final TweetHistory tweetHistory = tweetHistoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(tweetHistoryDTO, tweetHistory);
        tweetHistoryRepository.save(tweetHistory);
    }

    public void delete(final Long id) {
        tweetHistoryRepository.deleteById(id);
    }

    private TweetHistoryDTO mapToDTO(final TweetHistory tweetHistory,
            final TweetHistoryDTO tweetHistoryDTO) {
        tweetHistoryDTO.setId(tweetHistory.getId());
        tweetHistoryDTO.setMessage(tweetHistory.getMessage());
        tweetHistoryDTO.setWhenSent(tweetHistory.getWhenSent());
        tweetHistoryDTO.setTweetToHistory(tweetHistory.getTweetToHistory() == null ? null : tweetHistory.getTweetToHistory().getId());
        return tweetHistoryDTO;
    }

    private TweetHistory mapToEntity(final TweetHistoryDTO tweetHistoryDTO,
            final TweetHistory tweetHistory) {
        tweetHistory.setMessage(tweetHistoryDTO.getMessage());
        tweetHistory.setWhenSent(tweetHistoryDTO.getWhenSent());
        if (tweetHistoryDTO.getTweetToHistory() != null && 
                (tweetHistory.getTweetToHistory() == null || !tweetHistory.getTweetToHistory().getId().equals(tweetHistoryDTO.getTweetToHistory()))) {
            final Tweets tweetToHistory = tweetsRepository.findById(tweetHistoryDTO.getTweetToHistory())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "tweetToHistory not found"));
            tweetHistory.setTweetToHistory(tweetToHistory);
        }
        return tweetHistory;
    }

}
