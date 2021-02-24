package org.localomaha.reminders.tweeting;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// Start with https://stackoverflow.com/questions/49258766/spring-boot-2-0-x-disable-security-for-certain-profile
@Configuration
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/**");
    }
}
