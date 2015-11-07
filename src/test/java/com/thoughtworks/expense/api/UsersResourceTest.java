package com.thoughtworks.expense.api;

import com.thoughtworks.expense.core.User;
import com.thoughtworks.expense.core.UserRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UsersResourceTest extends JerseyTest {
    public UserRepository userRepository = mock(UserRepository.class);

    @Override
    public void setUp() throws Exception {
        User user1 = mock(User.class);
        when(user1.getId()).thenReturn(1);
        when(user1.getName()).thenReturn("James");
        User user2 = mock(User.class);
        List<User> list = Arrays.asList(user1, user2);
        when(userRepository.list()).thenReturn(list);
        super.setUp();
    }
    
    @Override
    protected Application configure() {
        return new ResourceConfig().register(new AbstractBinder() {


            @Override
            protected void configure() {
                bind(userRepository).to(UserRepository.class);
            }
        }).packages("com.thoughtworks.expense");
    }

    @Test
    public void should_list_all_users(){
        Response response = target("/users").request().get();

        List list = response.readEntity(List.class);

        assertThat(response.getStatus(), is(200));

        assertThat(list.size(), is(2));
        Map user = (Map) list.get(0);
        assertThat((String) user.get("uri"), is("/users/1"));
        assertThat((String) user.get("name"), is("James"));
        assertThat((Integer) user.get("id"), is(1));
    }
}
