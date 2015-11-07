package com.thoughtworks.expense.core;

public interface ExpenseItem {
    int getId();

    double getAmount();

    ExpenseItemCategory getCategory();

    String getName();

    void setName(String name);

    String getComment();
}
