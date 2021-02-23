package org.localomaha.reminders.tweeting.repos;

import org.localomaha.reminders.tweeting.domain.TweetHistory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TweetHistoryRepository extends JpaRepository<TweetHistory, Long> {
}
