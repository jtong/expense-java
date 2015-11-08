package com.thoughtworks.expense.api;

import com.thoughtworks.expense.core.User;
import com.thoughtworks.expense.core.UserRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/users")
public class UsersResource {

    @Inject
    private UserRepository userRepository;

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {

        List<User> list = userRepository.list();
        List<Map> result = new ArrayList<>();
        for(User user : list) {
            Map userBean = new HashMap();
            userBean.put("uri", "/users/"+user.getId());
            userBean.put("name", user.getName());
            userBean.put("id", user.getId());
            result.add(userBean);
        }
        return Response.status(200).entity(result).build();
    }
    
    @Path("/")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@FormParam("name") String name) {
        User user = userRepository.newInstance(name);
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", user.getName());
        result.put("uri", "/users/"+user.getId());
        result.put("id", user.getId());
        return Response.status(201).entity(result).build();
    }

    @Path("/{userId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("userId") int userId) {
        User theUser = userRepository.getUserById(userId);
        Map result = new HashMap();
        result.put("uri", "/users/" + userId);
        result.put("name", theUser.getName());
        result.put("id", theUser.getId());
        return Response.status(200).entity(result).build();
    }


    @Path("/{userId}/expense-requests")
    public ExpenseRequestResource expenseRequests() {
        return new ExpenseRequestResource(userRepository);
    }
    
    
}
