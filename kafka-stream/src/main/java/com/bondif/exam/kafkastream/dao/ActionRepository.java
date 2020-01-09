package com.bondif.exam.kafkastream.dao;

import com.bondif.exam.kafkastream.entities.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ActionRepository extends JpaRepository<Action, Long> {
    public Action findFirstByCompanyName(String companyName);
}
