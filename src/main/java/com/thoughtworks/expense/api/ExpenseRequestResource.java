package com.thoughtworks.expense.api;

import com.thoughtworks.expense.core.ExpenseRequest;
import com.thoughtworks.expense.core.UserRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
}
