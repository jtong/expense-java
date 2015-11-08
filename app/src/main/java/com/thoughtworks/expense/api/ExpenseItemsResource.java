package com.thoughtworks.expense.api;

import com.thoughtworks.expense.core.ExpenseItem;
import com.thoughtworks.expense.core.ExpenseItemCategory;
import com.thoughtworks.expense.core.UserRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseItemsResource {
    private UserRepository userRepository;

    public ExpenseItemsResource(UserRepository userRepository) {

        this.userRepository = userRepository;
    }
    
    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(@PathParam("expense-request-id") int requestId, @PathParam("userId") String userId) {
        ArrayList<Map> result = new ArrayList<>();
        List<ExpenseItem> expenseItems = userRepository.findExpenseItemsByRequestId(requestId);
        for(ExpenseItem expenseItem : expenseItems) {
            Map expenseItemBean = new HashMap();
            expenseItemBean.put("uri", "/users/" + userId + "/expense-requests/" + requestId + "/expense-items/" + expenseItem.getId());
            expenseItemBean.put("amount", expenseItem.getAmount());
            ExpenseItemCategory category = expenseItem.getCategory();
            if (category != null) {
                Map categoryBean = new HashMap();
                categoryBean.put("uri", "/expense-item-categories/" + category.getId());
                expenseItemBean.put("category", categoryBean);
            }
            result.add(expenseItemBean);
        }
        return Response.status(200).entity(result).build();

    }

    @Path("/")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@PathParam("expense-request-id") int requestId, @PathParam("userId") String userId,
                        @FormParam("name") String name) {
        HashMap result = new HashMap();
        ExpenseItem expenseItem = userRepository.newExpenseItem();
        expenseItem.setName(name);
        expenseItem = userRepository.createExpenseItem(expenseItem);
        result.put("uri", "/users/" + userId + "/expense-requests/" + requestId + "/expense-items/" + expenseItem.getId());
        result.put("name", expenseItem.getName());
        return Response.status(201).entity(result).build();

    }
    
    @Path("/{expenseItemId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("expenseItemId") int expenseItemId, @PathParam("userId") String userId, 
                       @PathParam("expense-request-id") String expenseRequestId){
        Map result = new HashMap();
        ExpenseItem instance = userRepository.getExpenseItemById(expenseItemId);
        result.put("uri", "/users/"+userId+"/expense-requests/"+expenseRequestId+ "/expense-items/"+instance.getId());
        result.put("amount", instance.getAmount());            
        ExpenseItemCategory category = instance.getCategory();
        if (category != null) {
            Map categoryBean = new HashMap();
            categoryBean.put("uri", "/expense-item-categories/" + category.getId());
            result.put("category", categoryBean);
        }
        
        return Response.status(200).entity(result).build();
    }
    
    @Path("/{expenseItemId}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("expenseItemId") int expenseItemId, @PathParam("userId") String userId,
                      @PathParam("expense-request-id") String expenseRequestId){
        Map result = new HashMap();
        ExpenseItem newInstance = userRepository.getExpenseItemById(expenseItemId);

        newInstance = userRepository.updateExpenseItem(newInstance);
        result.put("uri", "/users/"+userId+"/expense-requests/"+expenseRequestId+ "/expense-items/"+newInstance.getId());
        result.put("amount", newInstance.getAmount());
        result.put("comment",newInstance.getComment());
        ExpenseItemCategory category = newInstance.getCategory();

        if (category != null) {
            Map categoryBean = new HashMap();
            categoryBean.put("uri", "/expense-item-categories/" + category.getId());
            result.put("category", categoryBean);
        }

        return Response.status(200).entity(result).build();
    }
}
