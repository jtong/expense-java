package com.thoughtworks.expense.core;

import com.thoughtworks.expense.core.User;

import java.util.Date;

public interface ExpenseRequest {
    void approveBy(User manager);

    Date getRequestDate();

    int getAmount();

    User getRequester();

    User getApprover();

    int getId();

    String getStatus();

    void setStatus(String status);

    void setName(String name);

    void setApprover(User userById);
}
