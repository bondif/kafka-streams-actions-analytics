package com.bondif.exam.kafkastream;

import com.bondif.exam.kafkastream.entities.NewActionEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ActionsProcessor {

    @StreamListener
    @SendTo({"soldActions"})
    public KStream<String, Long> processSoldActions(@Input("actions") KStream<String, NewActionEvent> actionEventKStream) {
        return actionEventKStream
                .filter((k, v) -> v.getOrderType().equals("SALE"))
                .groupByKey()
                .windowedBy(TimeWindows.of(5000))
                .count(Materialized.as("soldActions")).toStream()
                .map((k, v) -> new KeyValue<>(k.key(), v));
    }
}
