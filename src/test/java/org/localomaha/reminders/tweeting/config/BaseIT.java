package org.localomaha.reminders.tweeting.config;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import org.localomaha.reminders.tweeting.TweetingApplication;
import org.localomaha.reminders.tweeting.repos.TweetHistoryRepository;
import org.localomaha.reminders.tweeting.repos.TweetsRepository;
import org.localomaha.reminders.tweeting.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.util.StreamUtils;
import org.testcontainers.containers.PostgreSQLContainer;


/**
 * Abstract base class to be extended by every IT test. Starts the Spring Boot context with a
 * Datasource connected to the Testcontainers Docker instance. The instance is reused for all tests,
 * with all data wiped out before each test.
 */
@SpringBootTest(
        classes = TweetingApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("it")
@Sql("/data/clearAll.sql")
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
public abstract class BaseIT {

    private static final PostgreSQLContainer postgreSQLContainer;

    static {
        postgreSQLContainer = (PostgreSQLContainer)(new PostgreSQLContainer("postgres:12.5")
                .withUsername("testcontainers")
                .withPassword("Testcontain3rs!")
                .withReuse(true));
        postgreSQLContainer.start();
    }

    @Autowired
    public TestRestTemplate restTemplate;

    @Autowired
    public TweetHistoryRepository tweetHistoryRepository;

    @Autowired
    public TweetsRepository tweetsRepository;

    @Autowired
    public UserRepository userRepository;

    @DynamicPropertySource
    public static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    public String readResource(final String resourceName) {
        try {
            return StreamUtils.copyToString(getClass().getResourceAsStream(resourceName), Charset.forName("UTF-8"));
        } catch (final IOException io) {
            throw new UncheckedIOException(io);
        }
    }

    public HttpHeaders headers() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}
