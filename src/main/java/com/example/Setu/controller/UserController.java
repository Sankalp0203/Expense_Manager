package com.example.Setu.controller;

import com.example.Setu.dto.ExpenseDto;
import com.example.Setu.database.SplitWiseDatabase;
import com.example.Setu.dto.GroupCreationDto;
import com.example.Setu.dto.SettlementRequestDto;
import com.example.Setu.model.*;
import com.example.Setu.service.ExpenseService;
import com.example.Setu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    SplitWiseDatabase splitWiseDatabase;

    @Autowired
    UserService userService;

    @GetMapping("{userId}")
    public ResponseEntity<User> getUser(@PathVariable(value="userId") Integer userId){
        User user = splitWiseDatabase.getUserStore().get(userId);
        return ResponseEntity.ok().body(user);
    }

    //returns null when no expense to settle
    @PostMapping("{groupId}/settleExpense")
    public Expense settleExpenseGroup(@PathVariable(value="groupId") Integer groupId, @RequestBody SettlementRequestDto settlementRequestDto){
        Group group = splitWiseDatabase.getGroupStore().get(groupId);
        User userA = splitWiseDatabase.getUserStore().get(settlementRequestDto.userIdA);
        User userB = splitWiseDatabase.getUserStore().get(settlementRequestDto.userIdB);
        Expense expense = userService.settleUp(userA, userB, group);
        return expense;
    }

    @PostMapping("/settleExpense")
    public List<Expense> settleNetExpenses(@RequestBody SettlementRequestDto settlementRequestDto){
        User userA = splitWiseDatabase.getUserStore().get(settlementRequestDto.userIdA);
        User userB = splitWiseDatabase.getUserStore().get(settlementRequestDto.userIdB);
        List<Expense> expenseList = userService.settleUp(userA, userB);
        return expenseList;
    }

    @GetMapping("/getNetUserBalance/{userId}")
    public ResponseEntity<UserPaymentMap> getNetUserBalances(@PathVariable("userId") Integer userId){
        User user = splitWiseDatabase.getUserStore().get(userId);
        UserPaymentMap userPaymentMap = userService.getNetUserBalance(user);
        return (ResponseEntity) ResponseEntity.ok(userPaymentMap);
    }

    @GetMapping("/getNetUserBalance/{userId}/{groupId}")
    public ResponseEntity<UserPaymentMap> getNetUserBalances(@PathVariable("userId") Integer userId,
                                                             @PathVariable("groupId") Integer groupId){
        User user = splitWiseDatabase.getUserStore().get(userId);
        Group group = splitWiseDatabase.getGroupStore().get(groupId);
        UserPaymentMap userPaymentMap = userService.getUserGroupBalances(user, group);
        return (ResponseEntity) ResponseEntity.ok(userPaymentMap);
    }

    @PostMapping("/createGroup")
    public ResponseEntity<Group> createGroup(@RequestBody GroupCreationDto groupCreationDto){
        Integer index = splitWiseDatabase.getGroupStore().size();
        Group group = new Group(index, groupCreationDto.getName(), groupCreationDto.getMemberSet(), new BalanceRegister(), new ArrayList<>());
        splitWiseDatabase.getGroupStore().put(index, group);
        return ResponseEntity.ok(group);
    }


}
