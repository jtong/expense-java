package com.thoughtworks.expense.api;

import com.thoughtworks.expense.core.ExpenseItem;
import com.thoughtworks.expense.core.ExpenseItemCategory;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExpenseItemsResourceTest extends TestBase {

    private final int requestId = 1;
    private String basePath;
    private ExpenseItem expenseItem;
    private ExpenseItemCategory expenseItemCategory;

    @Override
    public void setUp() throws Exception {
        basePath = "/users/1/expense-requests/1/expense-items";
        expenseItem = mock(ExpenseItem.class);
        when(expenseItem.getId()).thenReturn(requestId);
        when(expenseItem.getAmount()).thenReturn(200.0);
        expenseItemCategory = mock(ExpenseItemCategory.class);
        when(expenseItemCategory.getId()).thenReturn(1);
        when(expenseItem.getCategory()).thenReturn(expenseItemCategory);
        when(userRepository.findExpenseItemsByRequestId(1)).thenReturn(Arrays.asList(expenseItem));

        
        when(userRepository.getExpenseItemById(1)).thenReturn(expenseItem);
        when(userRepository.newExpenseItem()).thenReturn(mock(ExpenseItem.class));
        when(userRepository.createExpenseItem(any(ExpenseItem.class))).thenReturn(expenseItem);
        

        super.setUp();
    }

    


    @Test
    public void should_list_all_items(){
        Response response = target(basePath).request().get();

        assertThat(response.getStatus(), is(200));

        List items = response.readEntity(List.class);

        assertThat(items.size(), is(1));

        Map item = (Map) items.get(0);

        assertThat((String) item.get("uri"), is(basePath+"/1"));
        assertThat((Double) item.get("amount"), is(200.0));

        Map category = (Map) item.get("category");
        assertThat((String) category.get("uri"), is("/expense-item-categories/1"));
    }

    @Test
    public void should_create_item() {
        Form formData = new Form();
        String itemName = "Item-1";
        formData.param("name", itemName);
        when(expenseItem.getName()).thenReturn(itemName);
        Response response = target(basePath).request().post(Entity.entity(formData, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        assertThat(response.getStatus(), is(201));

        Map item = response.readEntity(Map.class);

        assertThat((String) item.get("name"), is(itemName));
        assertThat((String) item.get("uri"), is(basePath+"/1"));
        
    }

    @Test
    public void should_get_item_by_id() {
        Response response = target(basePath+"/1").request().get();
        
        assertThat(response.getStatus(), is(200));
        Map item = response.readEntity(Map.class);

        assertThat((String) item.get("uri"), is(basePath+"/1"));

        assertThat((Double) item.get("amount"), is(200.00));
        Map category = (Map) item.get("category");
        assertThat((String) category.get("uri"), is("/expense-item-categories/1"));
    }
    
    @Test
    public void should_update_item_by_id(){
        Form formData = new Form();
        formData.param("amount", String.valueOf(201.00));
        formData.param("categoryId", String.valueOf(1));
        formData.param("comment", "comment-1");
        ExpenseItem itemUpdated = mock(ExpenseItem.class);
        when(itemUpdated.getId()).thenReturn(1);
        when(itemUpdated.getAmount()).thenReturn(201.00);
        when(itemUpdated.getCategory()).thenReturn(expenseItemCategory);
        when(itemUpdated.getComment()).thenReturn("comment-1");
        when(userRepository.updateExpenseItem(any(ExpenseItem.class))).thenReturn(itemUpdated);
        Response response = target(basePath+"/1").request().put(Entity.entity(formData, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        assertThat(response.getStatus(), is(200));
        
        Map item = response.readEntity(Map.class);

        assertThat((String) item.get("uri"), is(basePath+"/1"));

        assertThat((Double) item.get("amount"), is(201.00));
        Map category = (Map) item.get("category");
        assertThat((String) category.get("uri"), is("/expense-item-categories/1"));
        assertThat((String) item.get("comment"), is("comment-1"));
    }
}
