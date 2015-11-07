package com.thoughtworks.expense.core;

import java.util.List;

public interface UserRepository {
    List<User> list();

    User create(User user);

    User newInstance(String name);
}
