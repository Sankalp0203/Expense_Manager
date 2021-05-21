package com.example.Setu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class Group {
    private final Integer id;
    private final String name;
    private final Set<Integer> memberSet;
    private final BalanceRegister balanceRegister;
    private final List<Expense> expenseList;
}
