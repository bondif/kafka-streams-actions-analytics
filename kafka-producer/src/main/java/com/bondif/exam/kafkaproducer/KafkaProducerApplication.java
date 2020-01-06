package com.bondif.exam.kafkaproducer;

import com.bondif.exam.kafkaproducer.dao.CompanyRepository;
import com.bondif.exam.kafkaproducer.entities.Company;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KafkaProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CompanyRepository companyRepository) {
        return args -> {
            companyRepository.save(new Company("A", "Oracle"));
            companyRepository.save(new Company("B", "Google"));
            companyRepository.save(new Company("C", "IBM"));
            companyRepository.save(new Company("D", "Facebook"));
            companyRepository.save(new Company("E", "Amazon"));
            companyRepository.save(new Company("F", "Microsoft"));
        };
    }

}
