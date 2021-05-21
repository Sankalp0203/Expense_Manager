package com.example.Setu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Expense {
    private final Integer id;
    private final Double total;
    private final String note;
    private final UserPaymentMap userPaymentMap;
    private final Boolean isPayment;
}
