package org.example.json;

import org.apache.kafka.common.serialization.Serdes;
import org.example.Tweet;



public class TweetSerde extends Serdes.WrapperSerde<Tweet> {

    public TweetSerde() {
        super(new TweetSerializer<>(), new TweetDeserializer<>(Tweet.class));
    }
}