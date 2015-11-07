package com.thoughtworks.expense.api;

import com.thoughtworks.expense.core.User;
import com.thoughtworks.expense.core.UserRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
    public List<Map> list() {

        List<User> list = userRepository.list();
        List<Map> result = new ArrayList<>();
        for(User user : list) {
            Map userBean = new HashMap();
            userBean.put("uri", "/users/"+user.getId());
            userBean.put("name", user.getName());
            userBean.put("id", user.getId());
            result.add(userBean);
        }
        return result;
    }
    
}
