package com.thoughtworks.expense.api;

import com.thoughtworks.expense.core.ExpenseItemCategoryRepository;
import com.thoughtworks.expense.core.Policy;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PoliciesResource {
    private ExpenseItemCategoryRepository expenseItemCategoryRepository;

    public PoliciesResource(ExpenseItemCategoryRepository expenseItemCategoryRepository) {

        this.expenseItemCategoryRepository = expenseItemCategoryRepository;
    }
    
    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map> list(@PathParam("expenseItemCategoryId") int expenseItemCategoryId){
        List<Map> result = new ArrayList<>();
        List<Policy> list = expenseItemCategoryRepository.findPoliciesByCategoryId(expenseItemCategoryId);
        for(Policy policy : list){
            Map policyBean = new HashMap();
            policyBean.put("uri", "/expenseItemCategories/" + expenseItemCategoryId + "/policies/" + policy.getId());
            policyBean.put("name", policy.getName());
            result.add(policyBean);
        }

        return result;
    }
    
    @Path("/")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map create(@PathParam("expenseItemCategoryId") int expenseItemCategoryId){
        Map result = new HashMap();
        Policy newInstance = expenseItemCategoryRepository.newPolicy();
        
        newInstance = expenseItemCategoryRepository.createPolicy(newInstance);
        
        result.put("uri","/expenseItemCategories/" + expenseItemCategoryId + "/policies/" + newInstance.getId() );
        result.put("name", newInstance.getName());
        result.put("publishDate", newInstance.getDate());
        
        return result;
    }
    @Path("/current")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map getCurrent(@PathParam("expenseItemCategoryId") int expenseItemCategoryId){
        Map result = new HashMap();
        Policy instance = expenseItemCategoryRepository.getCurrentPolicyByCategoryId(expenseItemCategoryId);
        result.put("uri", "/expenseItemCategories/" + expenseItemCategoryId + "/policies/" + instance.getId());
        result.put("name", instance.getName());
        result.put("publishDate", instance.getDate());
        return result;
    }
    
    @Path("/{policyId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map getById(@PathParam("expenseItemCategoryId") int expenseItemCategoryId, @PathParam("policyId")int policyId){
        Map result = new HashMap();
        Policy instance = expenseItemCategoryRepository.getPolicyById(policyId);
        result.put("uri", "/expenseItemCategories/" + expenseItemCategoryId + "/policies/" + instance.getId());
        result.put("name", instance.getName());
        result.put("publishDate", instance.getDate());
        return result;
    }
    
    @Path("/{policyId}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Map update(@PathParam("expenseItemCategoryId") int expenseItemCategoryId, @PathParam("policyId") int policyId){
        Map result = new HashMap();
        Policy policy = expenseItemCategoryRepository.getPolicyById(policyId);
        Policy instance = expenseItemCategoryRepository.updatePolicy(policy);
        result.put("uri", "/expenseItemCategories/" + expenseItemCategoryId + "/policies/" + instance.getId());
        result.put("name", instance.getName());
        result.put("publishDate", instance.getDate());
        return result;
    }
    

}
