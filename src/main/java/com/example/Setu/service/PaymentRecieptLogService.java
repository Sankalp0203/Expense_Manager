package com.example.Setu.service;

import com.example.Setu.database.SplitWiseDatabase;
import com.example.Setu.model.Expense;
import com.example.Setu.model.Group;
import com.example.Setu.model.PaymentReciept;
import com.example.Setu.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PaymentRecieptLogService {

    @Autowired
    SplitWiseDatabase splitWiseDatabase;

    public void addSettlementReciept(Expense expense, Group group){
        if(expense.getIsPayment()){
            PaymentReciept paymentReciept = new PaymentReciept();
            expense.getUserPaymentMap().getUserBalances().entrySet().stream().forEach((entry) ->{
                if(entry.getValue() > 0.0){
                    User payer = splitWiseDatabase.getUserStore().get(entry.getKey());
                    paymentReciept.setPayer(payer);
                    paymentReciept.setSettlementAmount(entry.getValue());
                } else if(entry.getValue() < 0){
                    User payee = splitWiseDatabase.getUserStore().get(entry.getKey());
                    paymentReciept.setPayee(payee);
                }
            });
            paymentReciept.setPaymentDate(new Date());
            paymentReciept.setGroup(group);
            splitWiseDatabase.getTransactionLog().add(paymentReciept);
        }
    }
}
