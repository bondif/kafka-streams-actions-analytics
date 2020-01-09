package com.bondif.exam.kafkastream.bindings;

import com.bondif.exam.kafkastream.entities.NewActionEvent;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface AnalyticsBinding {

    @Input("actions")
    KStream<String, NewActionEvent> newActionsIn();

    @Output("soldActions")
    KStream<String, Long> soldActionsOut();
}
