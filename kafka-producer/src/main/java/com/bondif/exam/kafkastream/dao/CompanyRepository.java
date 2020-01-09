package com.bondif.exam.kafkastream.dao;

import com.bondif.exam.kafkastream.entities.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyRepository extends MongoRepository<Company, String> {
}
