package com.example.Setu.service;

import com.example.Setu.database.SplitWiseDatabase;
import com.example.Setu.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService {

    @Autowired
    ExpenseService expenseService;

    @Autowired
    PaymentRecieptLogService paymentRecieptLogService;

    @Autowired
    SplitWiseDatabase splitWiseDatabase;

    public Expense settleUp(User A, User B, Group group){
        UserPaymentMap aUserPaymentMap = group.getBalanceRegister().getRegister().get(A.getId());
        if(aUserPaymentMap != null){
            Double amountToPay =  aUserPaymentMap.getUserBalances().get(B.getId());
            if(amountToPay != null && amountToPay != 0.0){
                UserPaymentMap userPaymentMap = new UserPaymentMap(new HashMap<>());
                if(amountToPay > 0){
                    userPaymentMap.getUserBalances().put(B.getId(), amountToPay);
                    userPaymentMap.getUserBalances().put(A.getId(), (-1)*amountToPay);
                } else if(amountToPay < 0){
                    userPaymentMap.getUserBalances().put(A.getId(), (-1)*amountToPay);
                    userPaymentMap.getUserBalances().put(B.getId(), amountToPay);
                }

                Expense expense = expenseService.addExpense(group, userPaymentMap, "Settlement Payment", amountToPay, true);
                paymentRecieptLogService.addSettlementReciept(expense, group);
                return expense;
            }
        }
        return null;
    }


    public List<Expense> settleUp(User A, User B){
        List<Expense> expenseList = new ArrayList<>();
        splitWiseDatabase.getGroupStore().entrySet().stream().filter(element -> {
            return (element.getValue().getMemberSet().contains(A.getId()) && (element.getValue().getMemberSet().contains(B.getId())));
        }).forEach(group -> {
            Expense expense = settleUp(A, B, group.getValue());
            if(expense!=null){
                expenseList.add(expense);
            }
        });

        return expenseList;
    }

    public UserPaymentMap getNetUserBalance(User user){
        UserPaymentMap paymentMap = new UserPaymentMap(new HashMap<>());
        splitWiseDatabase.getGroupStore().entrySet().stream().
                filter(element -> element.getValue().getMemberSet().contains(user.getId())).forEach(group -> {
                    UserPaymentMap userPaymentMap = getUserGroupBalances(user, group.getValue());
                    userPaymentMap.getUserBalances().entrySet().stream().forEach(paymentMapEntry ->{
                        Double balanceAmount = paymentMap.getUserBalances().getOrDefault(paymentMapEntry.getKey(), 0.0);
                        balanceAmount += paymentMapEntry.getValue();
                        paymentMap.getUserBalances().put(paymentMapEntry.getKey(), balanceAmount);
                    });
        });

        return paymentMap;
    }

    public UserPaymentMap getUserGroupBalances(User user, Group group){
        UserPaymentMap userPaymentMap =  group.getBalanceRegister().getRegister().getOrDefault(user.getId(), new UserPaymentMap(new HashMap<>()));
        return userPaymentMap;
    }
}
