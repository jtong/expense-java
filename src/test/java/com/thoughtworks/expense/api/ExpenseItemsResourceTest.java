package com.thoughtworks.expense.api;

import com.thoughtworks.expense.core.ExpenseItem;
import com.thoughtworks.expense.core.ExpenseItemCategory;
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

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExpenseItemsResourceTest extends TestBase {

    private final int requestId = 1;
    private String basePath;

    @Override
    public void setUp() throws Exception {
        basePath = "/users/1/expense-requests/1/expense-items/";
        ExpenseItem expenseItem = mock(ExpenseItem.class);
        when(expenseItem.getId()).thenReturn(requestId);
        when(expenseItem.getAmount()).thenReturn(200.0);
        ExpenseItemCategory expenseItemCategory = mock(ExpenseItemCategory.class);
        when(expenseItemCategory.getId()).thenReturn(1);
        when(expenseItem.getCategory()).thenReturn(expenseItemCategory);
        when(userRepository.findExpenseItemsByRequestId(1)).thenReturn(Arrays.asList(expenseItem));
        super.setUp();
    }

    


    @Test
    public void should_list_all_items(){
        Response response = target(basePath).request().get();

        assertThat(response.getStatus(), is(200));

        List items = response.readEntity(List.class);

        assertThat(items.size(), is(1));

        Map item = (Map) items.get(0);

        assertThat((String) item.get("uri"), is(basePath+"1"));
        assertThat((Double) item.get("amount"), is(200.0));

        Map category = (Map) item.get("category");
        assertThat((String) category.get("uri"), is("/expense-item-categories/1"));
    }


}
