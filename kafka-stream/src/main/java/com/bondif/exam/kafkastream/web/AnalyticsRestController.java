package com.bondif.exam.kafkastream.web;

import com.bondif.exam.kafkastream.dao.ActionRepository;
import com.bondif.exam.kafkastream.entities.Action;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class AnalyticsRestController {

    private ActionRepository actionRepository;

    @GetMapping("/analytics")
    public Collection<Action> soldActions() {
        return actionRepository.findAll();
    }
}
