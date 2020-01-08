package com.bondif.exam.kafkastream.config;

import com.bondif.exam.kafkastream.entities.NewActionEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafkaStreams
@EnableKafka
public class KafkaStreamConfig {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kafkaStreamsConfiguration() {
        Map<String, Object> config = new HashMap<>();

        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "actions-stream");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 1000);

        return new KafkaStreamsConfiguration(config);
    }

    @Bean
    public StreamsBuilderFactoryBean factoryBean() {
        return new StreamsBuilderFactoryBean(kafkaStreamsConfiguration());
    }

    @Bean
    public KStream<String, String> soldActions() throws Exception {
        KStream<String, String> stream = factoryBean().getObject().stream("actions");

        stream.foreach((k, v) -> {
            System.out.println(k + " :: " + v);
        });

//        KStream<String, Long> soldActions = stream
//                .map((k, v) -> new KeyValue<>(k, newActionEvent(v)))
//                .filter((k, v) -> v.getOrderType().equals("SALE"))
//                .groupByKey()
//                .windowedBy(TimeWindows.of(5000))
//                .count(Materialized.as("soldActions")).toStream()
//                .map((k, v) -> new KeyValue<>(k.key(), v));
//        soldActions.to("soldActions", Produced.valueSerde(Serdes.Long()));

//        KStream<String, Long> boughtActions = stream.
//                map((k, v) -> new KeyValue<>(k, newActionEvent(v)))
//                .filter((k, v) -> v.getOrderType().equals("BUY"))
//                .groupByKey()
//                .windowedBy(TimeWindows.of(5000))
//                .count(Materialized.as("boughtActions")).toStream()
//                .map((k, v) -> new KeyValue<>(k.key(), v));
//        boughtActions.to("boughtActions", Produced.valueSerde(Serdes.Long()));
//
//        KStream<String, Double> actionsAvgPrice = stream.
//                map((k, v) -> new KeyValue<>(k, newActionEvent(v)))
//                .groupByKey()
//                .windowedBy(TimeWindows.of(5000))
//                .reduce(this::actionsAvgPrice, Materialized.as("actionsAvgPrice")).toStream()
//                .map((k, v) -> new KeyValue<>(k.key(), v.getActionPrice()));
//        actionsAvgPrice.to("actionsAvgPrice", Produced.valueSerde(Serdes.Double()));

        return stream;
    }

    public NewActionEvent newActionEvent(String obj) {
        NewActionEvent actionEvent = new NewActionEvent();

        try {
            actionEvent = objectMapper.readValue(obj, NewActionEvent.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return actionEvent;
    }

    private NewActionEvent actionsAvgPrice(NewActionEvent actionEvent1, NewActionEvent actionEvent2) {
        return new NewActionEvent(actionEvent1.getCompanyName(),
                actionEvent1.getOrderType(),
                (actionEvent1.getActionPrice() + actionEvent2.getActionPrice()) / 2);
    }
}
