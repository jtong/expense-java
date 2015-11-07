package com.thoughtworks.expense.core;

import java.util.List;

public interface ExpenseItemCategoryRepository {
    List<ExpenseItemCategory> findExpenseItemCategories();

    ExpenseItemCategory newExpenseItemCategory();

    ExpenseItemCategory createExpenseItemCategory(ExpenseItemCategory newInstance);

    ExpenseItemCategory getCategoryById(int categoryId);


}
