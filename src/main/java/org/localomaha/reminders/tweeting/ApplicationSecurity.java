package org.localomaha.reminders.tweeting;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// Start with https://stackoverflow.com/questions/49258766/spring-boot-2-0-x-disable-security-for-certain-profile
@Configuration
@EnableScheduling // https://stackoverflow.com/questions/34436205/injecting-the-taskscheduler-with-spring/34437165
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/**");
    }

    @Bean
    public TaskScheduler taskScheduler() {
        //org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
        return new ThreadPoolTaskScheduler();
    }
}
