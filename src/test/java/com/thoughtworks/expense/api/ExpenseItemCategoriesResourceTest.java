package com.thoughtworks.expense.api;

import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ExpenseItemCategoriesResourceTest extends TestBase {
    private String basePath = "/expenseItemCategories";


    @Override
    public void setUp() throws Exception {
        super.setUp();
    }
    
    @Test
    public void should_list_all_categories(){
        Response response = target(basePath).request().get();

        assertThat(response.getStatus(), is(200));


    }


}