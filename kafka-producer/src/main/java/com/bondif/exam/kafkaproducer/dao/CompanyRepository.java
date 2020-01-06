package com.bondif.exam.kafkaproducer.dao;

import com.bondif.exam.kafkaproducer.entities.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyRepository extends MongoRepository<Company, String> {
}
