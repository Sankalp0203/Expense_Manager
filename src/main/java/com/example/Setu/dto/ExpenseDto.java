package com.example.Setu.dto;

import com.example.Setu.model.Group;
import com.example.Setu.model.UserPaymentMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ExpenseDto {
    private Integer group;
    private Double total;
    private String note;
    private Map<Integer, Double> userBalances;

}
