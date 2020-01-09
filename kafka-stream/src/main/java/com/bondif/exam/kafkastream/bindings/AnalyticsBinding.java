package com.bondif.exam.kafkastream.bindings;

import com.bondif.exam.kafkastream.entities.NewActionEvent;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface AnalyticsBinding {

    @Output("soldActionsStream")
    KStream<String, NewActionEvent> soldActionsStream();

    @Output("boughtActionsStream")
    KStream<String, NewActionEvent> boughtActionsStream();

    @Input("actions")
    KStream<String, NewActionEvent> newActionsIn();

    @Output("soldActions")
    KStream<String, Long> soldActionsOut();

    @Output("boughtActions")
    KStream<String, Long> boughtActionsOut();
}
