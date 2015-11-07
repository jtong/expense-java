package com.thoughtworks.expense.core;

import java.util.List;

public interface ExpenseItemCategoryRepository {
    List<ExpenseItemCategory> findExpenseItemCategories();
}
