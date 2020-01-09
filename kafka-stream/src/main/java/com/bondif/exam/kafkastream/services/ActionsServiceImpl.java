package com.bondif.exam.kafkastream.services;

import com.bondif.exam.kafkastream.dao.ActionRepository;
import com.bondif.exam.kafkastream.entities.Action;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ActionsServiceImpl implements ActionsService {

    private ActionRepository actionRepository;

    public ActionsServiceImpl(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    @Override
    public void incrementSoldActions(String company, Long actionsCount) {
        Action action = actionRepository.findFirstByCompanyName(company);

        if (action == null) {
            action = new Action(null, company, 0L, 0L, 0D);
        }
        action.setSoldActionsSum(action.getSoldActionsSum() + actionsCount);

        actionRepository.save(action);
    }
}
