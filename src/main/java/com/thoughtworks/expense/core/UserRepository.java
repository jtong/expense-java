package com.thoughtworks.expense.core;

import java.util.List;

public interface UserRepository {
    List<User> list();

    User create(User user);

    User newInstance(String name);

    User getUserById(int userId);

    List<ExpenseRequest> findExpenseRequestsByUserId(int userId);

    ExpenseRequest newExpenseRequest(User user);

    ExpenseRequest getExpenseRequestsById(int expenseId);

    ExpenseRequest updateExpenseRequest(ExpenseRequest expenseRequest);
}
