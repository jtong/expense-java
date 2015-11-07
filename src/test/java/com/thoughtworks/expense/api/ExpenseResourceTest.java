package com.thoughtworks.expense.api;

import com.thoughtworks.expense.core.ExpenseRequest;
import com.thoughtworks.expense.core.User;
import com.thoughtworks.expense.core.UserRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExpenseResourceTest extends JerseyTest {
    protected UserRepository userRepository = mock(UserRepository.class);
    private Date mockDate;

    @Override
    public void setUp() throws Exception {
        User user1 = mock(User.class);
        when(user1.getName()).thenReturn("James");
        Calendar instance = Calendar.getInstance();
        instance.set(2015, 11, 7);
        mockDate = instance.getTime();
        ExpenseRequest expenseRequest1 = mock(ExpenseRequest.class);
        when(expenseRequest1.getRequestDate()).thenReturn(mockDate);
        when(expenseRequest1.getRequester()).thenReturn(user1);
        when(expenseRequest1.getId()).thenReturn(1);
        
        ExpenseRequest expenseRequest2 = mock(ExpenseRequest.class);
        when(expenseRequest2.getRequestDate()).thenReturn(mockDate);
        when(expenseRequest2.getRequester()).thenReturn(user1);
        User user2 = mock(User.class);
        when(user2.getName()).thenReturn("Tom");
        when(expenseRequest2.getApprover()).thenReturn(user2);
        
        List<ExpenseRequest> list = Arrays.asList(expenseRequest1, expenseRequest2);
        when(userRepository.findExpenseRequestsByUserId(1)).thenReturn(list);
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
    public void should_list_all_expense_request(){
        Response response = target("/users/1/expense-requests").request().get();

        assertThat(response.getStatus(), is(200));
        List<Map> requests = response.readEntity(List.class);

        assertThat(requests.size(), is(2));

        Map expense1 = requests.get(0);
        Map expense2 = requests.get(1);
        assertThat((Long) expense1.get("requestDate"), is(mockDate.getTime()));
        assertThat((Integer) expense1.get("amount"), is(0));
        assertThat((String) expense1.get("uri"), is("/users/1/expense-requests/1"));

        assertThat((String) ((Map)expense1.get("requester")).get("name"), is("James"));
        assertThat((String)((Map)expense2.get("approver")).get("name"), is("Tom"));
    }


}
