package com.thoughtworks.expense.api;

import com.thoughtworks.expense.core.ExpenseRequest;
import com.thoughtworks.expense.core.User;
import com.thoughtworks.expense.core.UserRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.soap.MTOM;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExpenseResourceTest extends TestBase {
    private Date mockDate;
    private User user1;
    private User user2;

    @Override
    public void setUp() throws Exception {
        user1 = mock(User.class);
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
        user2 = mock(User.class);
        when(user2.getName()).thenReturn("Tom");
        when(user2.getId()).thenReturn(2);
        when(expenseRequest2.getApprover()).thenReturn(user2);
        
        List<ExpenseRequest> list = Arrays.asList(expenseRequest1, expenseRequest2);
        when(userRepository.findExpenseRequestsByUserId(1)).thenReturn(list);
        when(userRepository.getUserById(1)).thenReturn(user1);
        when(userRepository.newExpenseRequest(user1)).thenReturn(expenseRequest1);
        when(userRepository.getExpenseRequestsById(1)).thenReturn(expenseRequest1);
        ExpenseRequest expenseRequest3 = mock(ExpenseRequest.class);
        when(expenseRequest3.getRequestDate()).thenReturn(mockDate);
        when(expenseRequest3.getRequester()).thenReturn(user1);
        when(expenseRequest3.getId()).thenReturn(1);
        when(expenseRequest3.getApprover()).thenReturn(user2);
        when(expenseRequest3.getStatus()).thenReturn("APPROVED");
        when(userRepository.updateExpenseRequest(expenseRequest1)).thenReturn(expenseRequest3);
        super.setUp();
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
    
    @Test
    public void should_create_expense_request() {

        Form formData = new Form();
        formData.param("name", "expense-1");
        formData.param("requestId", String.valueOf(user2.getId()));
        Response response = target("/users/1/expense-requests").request().post(Entity.entity(formData, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        assertThat(response.getStatus(), is(200));

        Map expenseRequest = response.readEntity(Map.class);

        assertThat((String) expenseRequest.get("uri"), is("/users/1/expense-requests/1"));
        assertThat((Integer) expenseRequest.get("amount"), is(0));
        Map requester = (Map) expenseRequest.get("requester");
        assertThat((String) requester.get("uri"), is("/users/1"));

    }
    
    @Test
    public void should_get_expense_by_id(){
        Response response = target("/users/1/expense-requests/1").request().get();

        assertThat(response.getStatus(), is(200));

        Map expenseRequest = response.readEntity(Map.class);

        assertThat((String) expenseRequest.get("uri"), is("/users/1/expense-requests/1"));
        assertThat((Integer) expenseRequest.get("amount"), is(0));
        Map requester = (Map) expenseRequest.get("requester");
        assertThat((String) requester.get("uri"), is("/users/1"));   
    }

    @Test
    public void should_update_expense_request_by_id(){
        Form formData = new Form();
        formData.param("name", "expense-1");
        formData.param("approverId", String.valueOf(user2.getId()));
        formData.param("status", "APPROVED");
        Response response = target("/users/1/expense-requests/1").request().put(Entity.entity(formData, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        
        assertThat(response.getStatus(), is(200));
        Map expenseRequest = response.readEntity(Map.class);

        assertThat((String) expenseRequest.get("uri"), is("/users/1/expense-requests/1"));
        assertThat((Integer) expenseRequest.get("amount"), is(0));
        Map requester = (Map) expenseRequest.get("requester");
        assertThat((String) requester.get("uri"), is("/users/1"));
        Map approver = (Map) expenseRequest.get("approver");
        assertThat((String) approver.get("uri"), is("/users/2"));

        assertThat((String) expenseRequest.get("status"), is("APPROVED"));
                
    }
}
