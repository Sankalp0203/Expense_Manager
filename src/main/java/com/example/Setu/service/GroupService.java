package com.example.Setu.service;

import com.example.Setu.model.BalanceRegister;
import com.example.Setu.model.Expense;
import com.example.Setu.model.Group;
import com.example.Setu.model.UserPaymentMap;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GroupService {

    public void addGroupExpense(Group group, Expense expense){
        group.getExpenseList().add(expense);
        updateBalanceRegister(group);
    }

    public UserPaymentMap getSimplifiedUserPaymentMap(Group group){
        List<Expense> expenseList = group.getExpenseList();
        UserPaymentMap simplifiedMap = new UserPaymentMap(new HashMap<>());
        for(Expense expense: expenseList){
            expense.getUserPaymentMap().getUserBalances().forEach((userId, balanceAmount)->{
                Double currentAmount = simplifiedMap.getUserBalances().getOrDefault(userId, 0.0);
                Double finalAmount = currentAmount+balanceAmount;
                simplifiedMap.getUserBalances().put(userId, finalAmount);
            });
        }
        return simplifiedMap;
    }

    public void updateBalanceRegister(Group group){
        UserPaymentMap userPaymentMap = getSimplifiedUserPaymentMap(group);
        Comparator<Map.Entry<Integer, Double>> ascendingComparator = Comparator.comparing(Map.Entry::getValue);

        Comparator<Map.Entry<Integer, Double>> descendingComparator = (Map.Entry<Integer, Double> userPayment1,
                                                                      Map.Entry<Integer, Double> userPayment2) -> userPayment2.getValue().compareTo(userPayment1.getValue());

        PriorityQueue<Map.Entry<Integer, Double>> payeeEntries = new PriorityQueue<>(descendingComparator);
        PriorityQueue<Map.Entry<Integer, Double>> payerEntries = new PriorityQueue<>(ascendingComparator);
        group.getBalanceRegister().getRegister().clear();

        userPaymentMap.getUserBalances().entrySet().stream().forEach((entry)-> {
            if(entry.getValue() >0){
                payeeEntries.add(entry);
            } else if (entry.getValue() <0) {
                payerEntries.add(entry);
            }
        });

        while(!payeeEntries.isEmpty()){
            Map.Entry<Integer, Double> payeeEntry = payeeEntries.poll();
            Map.Entry<Integer, Double> payerEntry = payerEntries.poll();
            Double payeeBalance = payeeEntry.getValue();
            Double payerBalance = payerEntry.getValue()*-1;
            BalanceRegister balanceRegister = group.getBalanceRegister();

            UserPaymentMap payeePaymentMap = balanceRegister.getRegister().getOrDefault(payeeEntry.getKey(),
                    new UserPaymentMap(new HashMap<>()));
            UserPaymentMap payerPaymentMap = balanceRegister.getRegister().getOrDefault(payerEntry.getKey(),
                    new UserPaymentMap(new HashMap<>()));

            payeePaymentMap.getUserBalances().put(payerEntry.getKey(), Math.min(payeeBalance, payerBalance));
            payerPaymentMap.getUserBalances().put(payeeEntry.getKey(), -1*Math.min(payeeBalance, payerBalance));

            balanceRegister.getRegister().put(payeeEntry.getKey(), payeePaymentMap);
            balanceRegister.getRegister().put(payerEntry.getKey(), payerPaymentMap);

            Double remainingAmount = payeeBalance-payerBalance;
            if(remainingAmount > 0){
                payeeEntries.add(new AbstractMap.SimpleEntry<>(payeeEntry.getKey(), remainingAmount));
            } else if(remainingAmount < 0) {
                payerEntries.add(new AbstractMap.SimpleEntry<>(payerEntry.getKey(), remainingAmount));
            }
        }
    }
}
