package com.example.Setu.controller;

import com.example.Setu.database.SplitWiseDatabase;
import com.example.Setu.model.BalanceRegister;
import com.example.Setu.model.Expense;
import com.example.Setu.model.Group;
import com.example.Setu.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {
    @Autowired
    SplitWiseDatabase splitWiseDatabase;

    @GetMapping("{groupId}/balanceRegister")
    public ResponseEntity<BalanceRegister> getBalanceRegister(@PathVariable(value="groupId") Integer groupId){
        Group group = splitWiseDatabase.getGroupStore().get(groupId);
        BalanceRegister balanceRegister = group.getBalanceRegister();
        return ResponseEntity.ok().body(balanceRegister);
    }

    @GetMapping("{groupId}/expenseList")
    public ResponseEntity<List<Expense>> getExpenseList(@PathVariable(value="groupId") Integer groupId){
        Group group = splitWiseDatabase.getGroupStore().get(groupId);
        List<Expense> expenseList = group.getExpenseList();
        return ResponseEntity.ok().body(expenseList);
    }


}
