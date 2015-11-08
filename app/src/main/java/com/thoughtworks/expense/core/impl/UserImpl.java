package com.thoughtworks.expense.core.impl;

import com.thoughtworks.expense.core.User;

public class UserImpl implements User {
    private String name;
    private String id;

    public UserImpl(Integer id, String name) {
        this.id = String.valueOf(id);
        this.name = name;
    }

    public UserImpl(String id, String name) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String getName() {

        return name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setId(int id) {
        this.id = String.valueOf(id);
    }


}
