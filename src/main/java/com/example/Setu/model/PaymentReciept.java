package com.example.Setu.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PaymentReciept {
    private User payer;
    private User payee;
    private Date paymentDate;
    private Group group;
    private Double settlementAmount;
}
