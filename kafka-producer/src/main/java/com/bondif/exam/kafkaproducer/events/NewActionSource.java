package com.bondif.exam.kafkaproducer.events;

import com.bondif.exam.kafkaproducer.dao.CompanyRepository;
import com.bondif.exam.kafkaproducer.entities.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class NewActionSource implements ApplicationRunner {

    private KafkaTemplate kafkaTemplate;

    private CompanyRepository companyRepository;

    public NewActionSource(KafkaTemplate kafkaTemplate, CompanyRepository companyRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.companyRepository = companyRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Runnable runnable = () -> {
            List<String> ids = Arrays.asList("A", "B", "C", "D", "E", "F");
            List<String> orderTypes = Arrays.asList("SALE", "BUY");
            Company company = companyRepository.findById(ids.get(new Random().nextInt(ids.size()))).get();
            String orderType = orderTypes.get(new Random().nextInt(orderTypes.size()));
            NewActionEvent actionEvent = new NewActionEvent(company.getName(), orderType, new Random().nextDouble());
            kafkaTemplate.send("actions", actionEvent.getCompanyName(), actionEvent);
        };

        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(runnable, 1000, 10, TimeUnit.MILLISECONDS);
    }
}
