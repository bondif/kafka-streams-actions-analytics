package com.bondif.exam.kafkastream.listeners;

import com.bondif.exam.kafkastream.services.ActionsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AnalyticsConsumer {

    private ActionsService actionsService;

    public AnalyticsConsumer(ActionsService actionsService) {
        this.actionsService = actionsService;
    }

    @KafkaListener(topics = "soldActions", groupId = "trade")
    public void onSoldActions(ConsumerRecord message) {
        log.info("receiving : " + message.key() + " :: " + message.value());
        actionsService.incrementSoldActions(message.key().toString(), (Long) message.value());
    }

    @KafkaListener(topics = "boughtActions", groupId = "trade")
    public void onBoughtActions(ConsumerRecord message) {
        log.info("receiving buy : " + message.key() + " :: " + message.value());
        actionsService.incrementBoughtActions(message.key().toString(), (Long) message.value());
    }
}
