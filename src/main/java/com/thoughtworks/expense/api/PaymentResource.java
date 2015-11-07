package com.thoughtworks.expense.api;

import com.thoughtworks.expense.core.UserRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

public class PaymentResource {
    private UserRepository userRepository;

    public PaymentResource(UserRepository userRepository) {

        this.userRepository = userRepository;
    }
    
    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map getById(@PathParam("expense-request-id") int expenseRequestId,@PathParam("userId") String userId
                       ){
        Map result = new HashMap();
        Payment instance = userRepository.getPaymentByRequestId(expenseRequestId);
        result.put("uri", "/users/"+userId+"/expense-requests/"+expenseRequestId+ "/payment");
        result.put("amount", instance.getAmount());
        result.put("account", instance.getAccount());
        return result;
    }
}
