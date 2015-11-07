package com.thoughtworks.expense.api;

import com.thoughtworks.expense.core.ExpenseItemCategoryRepository;
import com.thoughtworks.expense.core.Policy;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
    
}
