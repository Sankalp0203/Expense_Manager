package com.example.Setu.database;

import com.example.Setu.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Getter
@Setter
@AllArgsConstructor
public class SplitWiseDatabase {
    private final Map<Integer, User> userStore;
    private final Map<Integer, Expense> expenseStore;
    private final Map<Integer, Group> groupStore;
    private final List<PaymentReciept> transactionLog;
}
