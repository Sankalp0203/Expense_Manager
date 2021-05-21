package com.example.Setu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor


public class BalanceRegister {
    private final Map<Integer, UserPaymentMap> register;

    public BalanceRegister(){
        register = new HashMap<>();
    }
}
