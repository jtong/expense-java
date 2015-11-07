package com.thoughtworks.expense.core.impl;

import com.thoughtworks.expense.core.User;

public class UserImp implements User {
    private String name;
    private int id;

    public UserImp(String name) {
        this.name = name;
    }

    @Override
    public String getName() {

        return name;
    }

    @Override
    public int getId() {
        return id;
    }
}
