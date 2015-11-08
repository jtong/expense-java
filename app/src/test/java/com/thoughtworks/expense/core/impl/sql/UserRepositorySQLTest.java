package com.thoughtworks.expense.core.impl.sql;

import com.thoughtworks.expense.core.User;
import com.thoughtworks.expense.core.UserRepository;
import com.thoughtworks.expense.core.impl.sql.UserRepositoryImpl;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UserRepositorySQLTest {
    @Test
    public void should_get_user_by_id() throws IOException {
        UserRepository userRepository = new UserRepositoryImpl();
        User user = userRepository.getUserById(1);

        String id = user.getId();

        assertThat(Integer.valueOf(id), is(1));
        assertThat(user.getName(), is("James"));
    }
}
