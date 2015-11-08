package com.thoughtworks.expense.api;

import com.thoughtworks.expense.core.UserRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    public Response getById(@PathParam("expense-request-id") int expenseRequestId,@PathParam("userId") String userId){
        Map result = new HashMap();
        Payment instance = userRepository.getPaymentByRequestId(expenseRequestId);
        result.put("uri", "/users/"+userId+"/expense-requests/"+expenseRequestId+ "/payment");
        result.put("amount", instance.getAmount());
        result.put("account", instance.getAccount());
        return Response.status(200).entity(result).build();
    }
    
    @Path("/")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@PathParam("expense-request-id") int expenseRequestId,@PathParam("userId") String userId){
        Map result = new HashMap();
        Payment newInstance = userRepository.newPayment(expenseRequestId);

        newInstance = userRepository.createPayment(newInstance);

        result.put("uri", "/users/"+userId+"/expense-requests/"+expenseRequestId+ "/payment");
        result.put("amount", newInstance.getAmount());
        result.put("account",newInstance.getAccount());


        return Response.status(201).entity(result).build();
    }
}
