package com.bondif.exam.kafkastream.processors;

import com.bondif.exam.kafkastream.entities.NewActionEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Predicate;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ActionsProcessor {

    @StreamListener
    @SendTo({"soldActionsStream", "boughtActionsStream"})
    public KStream<String, NewActionEvent>[] branchActions(@Input("actions") KStream<String, NewActionEvent> actionEventKStream) {
        Predicate<String, NewActionEvent> isSold = (k, v) -> v.getOrderType().equals("SALE");
        Predicate<String, NewActionEvent> isBought =  (k, v) -> v.getOrderType().equals("BUY");

        return actionEventKStream
                .branch(isSold, isBought);
    }

    @StreamListener
    @SendTo({"soldActions"})
    public KStream<String, Long> processSoldActions(@Input("soldActionsStream") KStream<String, NewActionEvent> actionEventKStream) {
        return actionEventKStream
                .groupByKey()
                .windowedBy(TimeWindows.of(5000))
                .count().toStream()
                .map((k, v) -> new KeyValue<>(k.key(), v));
    }

    @StreamListener
    @SendTo({"boughtActions"})
    public KStream<String, Long> processBoughtActions(@Input("boughtActionsStream") KStream<String, NewActionEvent> actionEventKStream) {
        return actionEventKStream
                .groupByKey()
                .windowedBy(TimeWindows.of(5000))
                .count().toStream()
                .map((k, v) -> new KeyValue<>(k.key(), v));
    }
}
