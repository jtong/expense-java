package com.thoughtworks.expense.api;

import com.thoughtworks.expense.core.ExpenseItemCategory;
import com.thoughtworks.expense.core.ExpenseItemCategoryRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/expenseItemCategories")
public class ExpenseItemCategoriesResource {
    @Inject
    private ExpenseItemCategoryRepository expenseItemCategoryRepository;
    
    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(){
        List<Map> result = new ArrayList<>();
        List<ExpenseItemCategory> list = expenseItemCategoryRepository.findExpenseItemCategories();
        for(ExpenseItemCategory  category: list){
            Map categoryBean = new HashMap();
            categoryBean.put("uri", "/expenseItemCategories/" + category.getId());
            categoryBean.put("name", category.getName());
            result.add(categoryBean);
        }
        
        return Response.status(200).entity(result).build();
    }
    
    @Path("/")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(){
        Map result = new HashMap();
        ExpenseItemCategory newInstance = expenseItemCategoryRepository.newExpenseItemCategory();

        newInstance = expenseItemCategoryRepository.createExpenseItemCategory(newInstance);

        result.put("uri", "/expenseItemCategories/" + newInstance.getId());
        result.put("id", newInstance.getId());
        result.put("name",newInstance.getName());


        return Response.status(201).entity(result).build();
    }
    
    @Path("/{expenseItemCategoryId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("expenseItemCategoryId") int expenseItemCategoryId){
        Map result = new HashMap();
        ExpenseItemCategory instance = expenseItemCategoryRepository.getExpenseItemCategoryById(expenseItemCategoryId);
        result.put("uri", "/expenseItemCategories/" + instance.getId());
        result.put("name", instance.getName());
        return Response.status(200).entity(result).build();
    }
    
    @Path("/{expenseItemCategoryId}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("expenseItemCategoryId") int expenseItemCategoryId){
        Map result = new HashMap();
        ExpenseItemCategory newInstance = expenseItemCategoryRepository.getExpenseItemCategoryById(expenseItemCategoryId);
        
        newInstance = expenseItemCategoryRepository.updateExpenseItemCategory(newInstance);
        
        result.put("uri", "/expenseItemCategories/" + newInstance.getId());
        result.put("name", newInstance.getName());

        
        return Response.status(200).entity(result).build();
    }

    @Path("/{expenseItemCategoryId}/policies")
    public  PoliciesResource getPoliciesResource(){
        return new PoliciesResource(expenseItemCategoryRepository);
    }
}
