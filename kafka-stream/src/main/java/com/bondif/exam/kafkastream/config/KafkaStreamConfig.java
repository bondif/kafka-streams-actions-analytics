package com.bondif.exam.kafkastream.config;

import com.bondif.exam.kafkastream.entities.NewActionEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.TimeWindows;
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

        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.57.3:9092");
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
    public KStream<String, String> process() throws Exception {
        KStream<String, String> stream = factoryBean().getObject().stream("actions");
//        KTable<String, String> analytics = stream.
//                map((k, v) -> new KeyValue<>(k, newActionEvent(v)))
//                .groupByKey()
//                .windowedBy(TimeWindows.of(5000));
                // TODO : send results to mysql database
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
}
