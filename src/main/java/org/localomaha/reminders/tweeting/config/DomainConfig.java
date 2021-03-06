package org.localomaha.reminders.tweeting.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan(basePackages = "org.localomaha.reminders.tweeting.domain")
@EnableJpaRepositories(basePackages = "org.localomaha.reminders.tweeting.repos")
@EnableTransactionManagement
public class DomainConfig {
}
