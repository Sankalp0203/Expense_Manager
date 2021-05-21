package com.example.Setu.controller;

import com.example.Setu.database.SplitWiseDatabase;
import com.example.Setu.dto.ExpenseDto;
import com.example.Setu.model.Expense;
import com.example.Setu.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    SplitWiseDatabase splitWiseDatabase;

    @Autowired
    com.example.Setu.service.ExpenseService expenseService;

    @PostMapping("{groupId}/addExpense")
    public ResponseEntity<Expense> addExpense(@PathVariable(value="groupId") Integer groupId, @RequestBody ExpenseDto expenseDto){
        Group group = splitWiseDatabase.getGroupStore().get(groupId);
        Expense expense = expenseService.addExpense(group, expenseDto, false);
        return ResponseEntity.ok().body(expense);
    }
}
