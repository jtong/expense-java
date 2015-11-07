package com.thoughtworks.expense.api;

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

public class ExpenseItemCategoriesResourceTest extends TestBase {
    private String basePath = "/expenseItemCategories";
    private ExpenseItemCategory category1;


    @Override
    public void setUp() throws Exception {
        category1 = mock(ExpenseItemCategory.class);
        when(category1.getId()).thenReturn(1);
        when(category1.getName()).thenReturn("categoryName");
        List<ExpenseItemCategory> findResult = Arrays.asList(category1);
        when(expenseItemCategoryRepository.findExpenseItemCategories()).thenReturn(findResult);
        super.setUp();
    }
    
    @Test
    public void should_list_all_categories(){
        Response response = target(basePath).request().get();

        assertThat(response.getStatus(), is(200));
        
        List<Map> result = response.readEntity(List.class);

        assertThat(result.size(), is(1));

        Map category = result.get(0);

        assertThat((String) category.get("uri"), is(basePath+"/1"));
        assertThat((String) category.get("name"), is("categoryName"));
    }

    @Test
    public void should_create_category(){
        Form formData = new Form();

        when(expenseItemCategoryRepository.newExpenseItemCategory()).thenReturn(category1);
        when(expenseItemCategoryRepository.createExpenseItemCategory(any(ExpenseItemCategory.class))).thenReturn(category1);
        
        Response response = target(basePath).request().post(Entity.entity(formData, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        assertThat(response.getStatus(), is(200));
        
        Map category = response.readEntity(Map.class);

        assertThat((String) category.get("uri"), is(basePath+"/1"));

        assertThat((String) category.get("name"), is("categoryName"));
        assertThat((Integer) category.get("id"), is(category1.getId()));
    }

}