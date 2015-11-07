package com.thoughtworks.expense.core;

import javafx.beans.value.ObservableBooleanValue;

import java.util.List;

public interface ExpenseItemCategoryRepository {
    List<ExpenseItemCategory> findExpenseItemCategories();

    ExpenseItemCategory newExpenseItemCategory();

    ExpenseItemCategory createExpenseItemCategory(ExpenseItemCategory newInstance);

    ExpenseItemCategory getExpenseItemCategoryById(int categoryId);


    ExpenseItemCategory updateExpenseItemCategory(ExpenseItemCategory newInstance);

    List<Policy> findPoliciesByCategoryId(int categoryId);

    Policy newPolicy();

    Policy createPolicy(Policy newInstance);

    Policy getPolicyById(int policyId);
}
