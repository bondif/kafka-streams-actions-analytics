package com.bondif.exam.kafkastream;

import com.bondif.exam.kafkastream.bindings.AnalyticsBinding;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(AnalyticsBinding.class)
@Slf4j
public class KafkaStreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaStreamApplication.class, args);
    }
}
