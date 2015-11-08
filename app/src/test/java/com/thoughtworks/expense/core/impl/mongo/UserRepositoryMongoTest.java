package com.thoughtworks.expense.core.impl.mongo;

import com.thoughtworks.expense.core.User;
import com.thoughtworks.expense.core.UserRepository;
import com.thoughtworks.expense.core.impl.mongo.UserRepositoryMongoImpl;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UserRepositoryMongoTest {
    @Test
    public void should_get_user_by_id() throws IOException {
        UserRepository userRepository = new UserRepositoryMongoImpl();
        User user = userRepository.getUserById("563ec6728729d479ddffaa9b");

        assertThat(user.getId(), is("563ec6728729d479ddffaa9b"));
        assertThat(user.getName(), is("James"));
    }
}
