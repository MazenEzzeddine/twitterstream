package org.example.json;

import com.google.gson.Gson;
import org.example.Tweet;
import java.nio.charset.StandardCharsets;
import org.apache.kafka.common.serialization.Serializer;

/*
class TweetSerializer implements Serializer<Tweet> {
    private Gson gson = new Gson();

    @Override
    public byte[] serialize(String topic, Tweet tweet) {
        if (tweet == null) return null;
        return gson.toJson(tweet).getBytes(StandardCharsets.UTF_8);
    }
}*/



import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.apache.kafka.common.serialization.Serializer;

class TweetSerializer<T> implements Serializer<T> {
    private Gson gson =
            new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();

    /** Default constructor needed by Kafka */
    public TweetSerializer() {}

    @Override
    public void configure(Map<String, ?> props, boolean isKey) {}

    @Override
    public byte[] serialize(String topic, T type) {
        return gson.toJson(type).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void close() {}
}
