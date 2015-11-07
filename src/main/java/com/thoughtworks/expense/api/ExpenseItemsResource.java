package com.thoughtworks.expense.api;

import com.thoughtworks.expense.core.ExpenseItem;
import com.thoughtworks.expense.core.ExpenseItemCategory;
import com.thoughtworks.expense.core.UserRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
    public List<Map> list(@PathParam("expense-request-id") int requestId, @PathParam("userId") String userId) {
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
                expenseItemBean.put("category",categoryBean);
            }
            result.add(expenseItemBean);
        }
        return result;

    } 
}