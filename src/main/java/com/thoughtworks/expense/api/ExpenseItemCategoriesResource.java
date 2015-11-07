package com.thoughtworks.expense.api;

import com.thoughtworks.expense.core.ExpenseItemCategory;
import com.thoughtworks.expense.core.ExpenseItemCategoryRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
    public List<Map> list(){
        List<Map> result = new ArrayList<>();
        List<ExpenseItemCategory> list = expenseItemCategoryRepository.findExpenseItemCategories();
        for(ExpenseItemCategory  category: list){
            Map categoryBean = new HashMap();
            categoryBean.put("uri", "/expenseItemCategories/" + category.getId());
            categoryBean.put("name", category.getName());
            result.add(categoryBean);
        }
        
        return result;
    }
    
    @Path("/")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map create(){
        Map result = new HashMap();
        ExpenseItemCategory newInstance = expenseItemCategoryRepository.newExpenseItemCategory();

        newInstance = expenseItemCategoryRepository.createExpenseItemCategory(newInstance);

        result.put("uri", "/expenseItemCategories/" + newInstance.getId());
        result.put("id", newInstance.getId());
        result.put("name",newInstance.getName());


        return result;
    }
}
