package com.thoughtworks.expense.core;

public interface ExpenseItem {
    int getId();

    double getAmount();

    ExpenseItemCategory getCategory();
}
