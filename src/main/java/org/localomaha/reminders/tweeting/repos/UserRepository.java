package org.localomaha.reminders.tweeting.repos;

import org.localomaha.reminders.tweeting.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
