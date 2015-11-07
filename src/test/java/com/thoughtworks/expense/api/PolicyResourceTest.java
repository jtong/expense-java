package com.thoughtworks.expense.api;

import com.thoughtworks.expense.core.Policy;
import org.junit.Test;

import javax.ws.rs.core.Response;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PolicyResourceTest extends TestBase {
    private String basePath = "/expenseItemCategories/1/policies";
    
    
    @Override
    public void setUp() throws Exception {
        Policy policy1 = mock(Policy.class);
        when(policy1.getId()).thenReturn(1);
        when(policy1.getName()).thenReturn("policy name");
        List<Policy> policiesResources = Arrays.asList(policy1);
        
        when(expenseItemCategoryRepository.findPoliciesByCategoryId(1)).thenReturn(policiesResources);
        super.setUp();
    }
    
    @Test
    public void should_list_all_policy(){
        Response response = target(basePath).request().get();
        
        assertThat(response.getStatus(), is(200));
        List<Map> result = response.readEntity(List.class);

        assertThat(result.size(), is(1));

        Map policy = result.get(0);

        assertThat((String) policy.get("uri"), is(basePath+"/1"));
        assertThat((String) policy.get("name"), is("policy name"));
    }

    
}