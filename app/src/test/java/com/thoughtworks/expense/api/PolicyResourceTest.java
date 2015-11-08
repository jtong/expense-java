package com.thoughtworks.expense.api;

import com.thoughtworks.expense.core.Policy;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PolicyResourceTest extends TestBase {
    private String basePath = "/expenseItemCategories/1/policies";
    private Policy policy1;


    @Override
    public void setUp() throws Exception {
        policy1 = mock(Policy.class);
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

    @Test
    public void should_create_policy() {
        Form formData = new Form();

        when(expenseItemCategoryRepository.createPolicy(any(Policy.class))).thenReturn(policy1);
        when(policy1.getDate()).thenReturn(new Date());
        Response response = target(basePath).request().post(Entity.entity(formData, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        assertThat(response.getStatus(), is(201));

        Map policy = response.readEntity(Map.class);

        assertThat((String) policy.get("uri"), is(basePath+"/1"));
        assertThat((String) policy.get("name"), is(policy1.getName()));
        assertThat((Long) policy.get("publishDate"), is(policy1.getDate().getTime()));
        
    }
    
    @Test
    public void should_get_by_id(){
        when(expenseItemCategoryRepository.getPolicyById(1)).thenReturn(policy1);
        when(policy1.getDate()).thenReturn(new Date());

        Response response = target(basePath+"/1").request().get();

        assertThat(response.getStatus(), is(200));
        
        Map policy = response.readEntity(Map.class);

        assertThat((String) policy.get("uri"), is(basePath+"/1"));
        assertThat((String) policy.get("name"), is(policy1.getName()));
        assertThat((Long) policy.get("publishDate"), is(policy1.getDate().getTime()));
    }
    
    @Test
    public void should_update_policy(){
        Form formData = new Form();
        when(policy1.getDate()).thenReturn(new Date());
        when(expenseItemCategoryRepository.updatePolicy(any(Policy.class))).thenReturn(policy1);

        Response response = target(basePath+"/1").request().put(Entity.entity(formData, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        assertThat(response.getStatus(), is(200));
        
        Map policy = response.readEntity(Map.class);

        assertThat((String) policy.get("uri"), is(basePath+"/1"));
        assertThat((String) policy.get("name"), is(policy1.getName()));
        assertThat((Long) policy.get("publishDate"), is(policy1.getDate().getTime()));
    }
    
    @Test
    public void should_get_current(){
        when(policy1.getDate()).thenReturn(new Date());

        int categoryId = 1;
        when(expenseItemCategoryRepository.getCurrentPolicyByCategoryId(categoryId)).thenReturn(policy1);
        Response response = target(basePath+"/current").request().get();

        assertThat(response.getStatus(), is(200));
        Map policy = response.readEntity(Map.class);

        assertThat((String) policy.get("uri"), is(basePath+"/1"));
        assertThat((String) policy.get("name"), is(policy1.getName()));
        assertThat((Long) policy.get("publishDate"), is(policy1.getDate().getTime()));


    }
    
}