package org.example.json;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.example.Tweet;

/*
public class TweetSerdes implements Serde<Tweet> {

    @Override
    public Serializer<Tweet> serializer() {
        return new TweetSerializer();
    }

    @Override
    public Deserializer<Tweet> deserializer() {
        return new TweetDeserializer();
    }
}*/



public class TweetSerdes {

    public static Serde<Tweet> Tweet() {
        TweetSerializer<Tweet> serializer = new TweetSerializer<>();
        TweetDeserializer<Tweet> deserializer = new TweetDeserializer<>(Tweet.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }

   /* public static Serde<BodyTemp> BodyTemp() {
        JsonSerializer<BodyTemp> serializer = new JsonSerializer<>();
        JsonDeserializer<BodyTemp> deserializer = new JsonDeserializer<>(BodyTemp.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }

    public static Serde<CombinedVitals> CombinedVitals() {
        JsonSerializer<CombinedVitals> serializer = new JsonSerializer<>();
        JsonDeserializer<CombinedVitals> deserializer = new JsonDeserializer<>(CombinedVitals.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }*/
}
