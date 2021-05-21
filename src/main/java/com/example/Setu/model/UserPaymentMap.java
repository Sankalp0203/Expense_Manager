package com.example.Setu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor

//All +ve sums indicates that this useer will pay -ve indicates this user will get
public class UserPaymentMap {
    private final Map<Integer, Double> userBalances;
}
