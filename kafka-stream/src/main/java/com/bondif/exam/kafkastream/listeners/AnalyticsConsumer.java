package com.bondif.exam.kafkastream.listeners;

import com.bondif.exam.kafkastream.dao.ActionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AnalyticsConsumer {

    private ActionRepository actionRepository;
    private ObjectMapper objectMapper;

    public AnalyticsConsumer(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
        this.objectMapper = new ObjectMapper();
    }

    @KafkaListener(topics = "soldActions", groupId = "trade")
    public void onMessage(ConsumerRecord message) {
//        NewActionEvent actionEvent = objectMapper.readValue(message.value(), NewActionEvent.class);
        log.info("receiving : " + message.key() + " :: " + message.value());
    }
}
