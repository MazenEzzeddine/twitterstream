package org.example;

import java.util.Properties;

import io.micrometer.core.instrument.binder.kafka.KafkaStreamsMetrics;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.example.json.TweetSerdes;
import org.example.json.TweetSerde;



class App {
    public static void main(String[] args) {
       // Topology topology = SimpleTopology.buildV1();


        Topology topology = new Topology();



        topology.addSource("TwitterSource", "testtopic1");
        topology.addProcessor("PrintTwitterProcessor", PrintProcessor::new, "TwitterSource");

        PrometheusUtils.initPrometheus();


        // set the required properties for running Kafka Streams
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "Twitter");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "my-cluster-kafka-bootstrap:9092");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, TweetSerde.class.getName());

        // config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
        // config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
        // config.put("schema.registry.url", "http://localhost:8081");

        // build the topology and start streaming!


        StreamsBuilder builder = new StreamsBuilder();

        Serde<String> stringSerde = Serdes.String();
        Serde<Tweet> TweetSerde = TweetSerdes.Tweet();

     /*  KStream<String, Tweet> stream =  builder.stream("testtopic1",Consumed.with(stringSerde, TweetSerde));

        Topology topo = builder.build();

        topology.addProcessor("PrintTwitterProcessor", PrintProcessor::new, "TwitterSource");



        KafkaStreams mystream =*/


        KafkaStreams streams = new KafkaStreams(topology, config);



        KafkaStreamsMetrics metrics = new KafkaStreamsMetrics(streams);
        metrics.bindTo(PrometheusUtils.prometheusRegistry);


        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

        System.out.println("Starting Twitter streams");
        streams.start();
    }
}