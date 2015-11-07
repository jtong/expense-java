package com.thoughtworks.expense.api;

import com.thoughtworks.expense.core.ExpenseRequest;
import com.thoughtworks.expense.core.User;
import com.thoughtworks.expense.core.UserRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseRequestResource {
    private UserRepository userRepository;

    public ExpenseRequestResource(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map> list(@PathParam("userId") int userId) {
        ArrayList<Map> result = new ArrayList<Map>();

        List<ExpenseRequest> expenseRequests = userRepository.findExpenseRequestsByUserId(userId);
        for(ExpenseRequest expenseRequest : expenseRequests) {
            HashMap expenseRequestBean = new HashMap();
            expenseRequestBean.put("requestDate", expenseRequest.getRequestDate());
            expenseRequestBean.put("amount", expenseRequest.getAmount());
            expenseRequestBean.put("uri", "/users/"+ userId+"/expense-requests/"+expenseRequest.getId());
            Map requester = new HashMap();
            requester.put("name", expenseRequest.getRequester().getName());
            requester.put("uri", "/users/" + expenseRequest.getRequester().getId());
            expenseRequestBean.put("requester", requester);

            if (expenseRequest.getApprover() != null) {
                Map approver = new HashMap();
                approver.put("name", expenseRequest.getApprover().getName());
                approver.put("uri", "users/" + expenseRequest.getApprover().getId());
                expenseRequestBean.put("approver", approver);
            }
            result.add(expenseRequestBean);
        }
        
        return result;
    }

    @Path("/")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map getById(@PathParam("userId") int userId) {
        User user = userRepository.getUserById(userId);
        ExpenseRequest expenseRequest = userRepository.newExpenseRequest(user);

        HashMap result = new HashMap();
        result.put("amount", expenseRequest.getAmount());
        result.put("uri", "/users/" + userId + "/expense-requests/" + expenseRequest.getId());
        Map requester = new HashMap();
        requester.put("uri", "/users/" + userId);
        result.put("requester", requester);
        return result;
    }

    @Path("/{expense-request-id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map getById(@PathParam("userId") int userId, @PathParam("expense-request-id") int expenseId) {
        ExpenseRequest expenseRequest = userRepository.getExpenseRequestsById(expenseId);
        
        HashMap result = new HashMap();
        result.put("amount", expenseRequest.getAmount());
        result.put("uri", "/users/" + userId + "/expense-requests/" + expenseRequest.getId());
        Map requester = new HashMap();
        requester.put("uri", "/users/" + userId);
        result.put("requester", requester);
        return result;
    }



    @Path("/{expense-request-id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Map getById(@PathParam("userId") int userId, @PathParam("expense-request-id") int expenseId,
                        @FormParam("name") String name, @FormParam("approverId") int approverId,
                        @FormParam("status") String status) {
        ExpenseRequest expenseRequest = userRepository.getExpenseRequestsById(expenseId);
        expenseRequest.setStatus(status);
        expenseRequest.setName(name);
        expenseRequest.setApprover(userRepository.getUserById(approverId));
        expenseRequest = userRepository.updateExpenseRequest(expenseRequest);
        HashMap result = new HashMap();
        result.put("amount", expenseRequest.getAmount());
        result.put("uri", "/users/" + userId + "/expense-requests/" + expenseRequest.getId());
        Map requester = new HashMap();
        requester.put("uri", "/users/" + userId);
        result.put("requester", requester);
        
        if(expenseRequest.getApprover() != null){
            Map approver = new HashMap();
            approver.put("uri", "/users/"+ expenseRequest.getApprover().getId());
            result.put("approver", approver);
        }

        result.put("status", expenseRequest.getStatus());
        return result;
    }


    @Path("/{expense-request-id}/expense-items")
    @Produces(MediaType.APPLICATION_JSON)
    public ExpenseItemsResource getExpenseItemResource() {
        return new ExpenseItemsResource(userRepository);

    }
}
