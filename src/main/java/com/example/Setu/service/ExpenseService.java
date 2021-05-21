package com.example.Setu.service;

import com.example.Setu.dto.ExpenseDto;
import com.example.Setu.database.SplitWiseDatabase;
import com.example.Setu.model.Expense;
import com.example.Setu.model.Group;
import com.example.Setu.model.UserPaymentMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {

    @Autowired
    SplitWiseDatabase splitWiseDatabase;
    @Autowired
    GroupService groupService;

    public Expense addExpense(Group group, ExpenseDto expenseDto, Boolean isPayment){
        return addExpense(group,new UserPaymentMap(expenseDto.getUserBalances()), expenseDto.getNote(),expenseDto.getTotal(), isPayment);
    }

    public Expense addExpense(Group group, UserPaymentMap userPaymentMap, String note, Double total, Boolean isPayment){
        //first validate the payment Map Net sum should be 0.
        Integer id = splitWiseDatabase.getExpenseStore().size();
        Expense expense = new Expense(id, total, note, userPaymentMap, isPayment);
        splitWiseDatabase.getExpenseStore().put(id, expense);
        groupService.addGroupExpense(group, expense);
        return expense;
    }


}
