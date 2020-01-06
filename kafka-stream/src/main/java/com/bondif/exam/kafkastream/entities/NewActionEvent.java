package com.bondif.exam.kafkastream.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewActionEvent {
    private String companyName;
    private String orderType;
    private Double actionPrice;
}
