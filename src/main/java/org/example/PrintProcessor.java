package org.example;


import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.Record;

public class PrintProcessor implements Processor<String, Tweet, String , Tweet> {
    @Override
    public void init(ProcessorContext<String, Tweet> context) {
        // no special initialization needed in this example
    }

    @Override
    public void process(Record<String, Tweet> record) {

        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("(Processor API) Hello, " + record.toString());
    }

    @Override
    public void close() {
        // no special clean up needed in this example
    }
}