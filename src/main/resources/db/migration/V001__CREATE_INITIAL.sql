CREATE SEQUENCE  IF NOT EXISTS primary_sequence START WITH 10000 INCREMENT BY 1;

CREATE TABLE "user" (
    id BIGINT NOT NULL,
    twitter_handle VARCHAR(255),
    name VARCHAR(255),
    email VARCHAR(255),
    CONSTRAINT USER_PKEY PRIMARY KEY (id)
);

CREATE TABLE tweets (
    id BIGINT NOT NULL,
    message VARCHAR(255),
    crontab VARCHAR(255),
    user_tweets_id BIGINT,
    CONSTRAINT TWEETS_PKEY PRIMARY KEY (id)
);

CREATE TABLE tweet_history (
    id BIGINT NOT NULL,
    message VARCHAR(255),
    when_sent TIMESTAMP WITHOUT TIME ZONE,
    tweet_to_history_id BIGINT,
    CONSTRAINT TWEET_HISTORY_PKEY PRIMARY KEY (id)
);

ALTER TABLE tweets ADD CONSTRAINT fk_tweets_user_tweets_id FOREIGN KEY (user_tweets_id) REFERENCES "user" (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE tweet_history ADD CONSTRAINT fk_tweet_history_tweet_to_history_id FOREIGN KEY (tweet_to_history_id) REFERENCES tweets (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

