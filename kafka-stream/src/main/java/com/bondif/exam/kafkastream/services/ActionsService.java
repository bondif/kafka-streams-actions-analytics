package com.bondif.exam.kafkastream.services;

public interface ActionsService {
    public void incrementSoldActions(String company, Long actionsCount);

    public void incrementBoughtActions(String toString, Long value);
}
