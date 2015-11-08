package com.thoughtworks.expense.api;

import com.thoughtworks.expense.core.User;
import com.thoughtworks.expense.core.UserRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.hamcrest.core.Is;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UsersResourceTest extends TestBase {

    @Override
    public void setUp() throws Exception {
        User user1 = mock(User.class);
        when(user1.getId()).thenReturn("1");
        when(user1.getName()).thenReturn("James");
        User user2 = mock(User.class);
        List<User> list = Arrays.asList(user1, user2);
        when(userRepository.list()).thenReturn(list);
        when(userRepository.newInstance("James")).thenReturn(user1);
        when(userRepository.create(user1)).thenReturn(user1);

        when(userRepository.getUserById(1)).thenReturn(user1);
        super.setUp();
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
        assertThat((String) user.get("id"), is("1"));
    }
    
    @Test
    public void should_create_user() {
        Form formData = new Form();
        formData.param("name", "James");

        Response response = target("/users").request().post(Entity.entity(formData, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        assertThat(response.getStatus(), is(201));

        Map user = response.readEntity(Map.class);
        assertThat((String) user.get("uri"), is("/users/1"));
        assertThat((String) user.get("name"), is("James"));
        assertThat((String) user.get("id"), is("1"));
    }
    
    @Test
    public void should_get_user_by_id(){
        Response response = target("/users/1").request().get();

        assertThat(response.getStatus(), is(200));
        Map user = response.readEntity(Map.class);
        assertThat((String) user.get("uri"), is("/users/1"));
        assertThat((String) user.get("name"), is("James"));
        assertThat((String) user.get("id"), is("1"));
        
    }
    
}
