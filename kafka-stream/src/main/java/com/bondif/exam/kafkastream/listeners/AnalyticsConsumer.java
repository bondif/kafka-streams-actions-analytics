package com.bondif.exam.kafkastream.listeners;

import com.bondif.exam.kafkastream.dao.ActionRepository;
import com.bondif.exam.kafkastream.entities.NewActionEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsConsumer {

    private ActionRepository actionRepository;
    private ObjectMapper objectMapper;

    public AnalyticsConsumer(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
        this.objectMapper = new ObjectMapper();
    }

    @KafkaListener(topics = "actions", groupId = "trade")
    public void onMessage(ConsumerRecord message) {
//        NewActionEvent actionEvent = objectMapper.readValue(message.value(), NewActionEvent.class);
        System.out.println(message.key() + " : " + message.value());
    }
}
