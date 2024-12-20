package org.example.json;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.Tweet;
import java.nio.charset.StandardCharsets;
import org.apache.kafka.common.serialization.Deserializer;

/*
public class TweetDeserializer implements Deserializer<Tweet> {
    private Gson gson =
            new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

    @Override
    public Tweet deserialize(String topic, byte[] bytes) {
        if (bytes == null) return null;
        return gson.fromJson(new String(bytes, StandardCharsets.UTF_8), Tweet.class);
    }
}*/



import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;

public class TweetDeserializer<T> implements Deserializer<T> {
    private Gson gson =
            new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();


    public TweetDeserializer() {
    }

    private Class<T> destinationClass;
    private Type reflectionTypeToken;

    /** Default constructor needed by Kafka */
    public TweetDeserializer(Class<T> destinationClass) {
        this.destinationClass = destinationClass;
    }

    public TweetDeserializer(Type reflectionTypeToken) {
        this.reflectionTypeToken = reflectionTypeToken;
    }

    @Override
    public void configure(Map<String, ?> props, boolean isKey) {}

    @Override
    public T deserialize(String topic, byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        Type type = destinationClass != null ? destinationClass : reflectionTypeToken;
        return gson.fromJson(new String(bytes), type);
    }

    @Override
    public void close() {}
}
