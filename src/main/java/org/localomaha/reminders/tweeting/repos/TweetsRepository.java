package org.localomaha.reminders.tweeting.repos;

import org.localomaha.reminders.tweeting.domain.Tweets;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TweetsRepository extends JpaRepository<Tweets, Long> {
}
