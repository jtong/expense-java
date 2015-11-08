package com.thoughtworks.expense.core.impl.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.thoughtworks.expense.api.Payment;
import com.thoughtworks.expense.core.ExpenseItem;
import com.thoughtworks.expense.core.ExpenseRequest;
import com.thoughtworks.expense.core.User;
import com.thoughtworks.expense.core.UserRepository;
import com.thoughtworks.expense.core.impl.UserImpl;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class UserRepositoryMongoImpl implements UserRepository {


    private final MongoClient mongoClient;

    public UserRepositoryMongoImpl() throws IOException {
        mongoClient = new MongoClient();

    }

    @Override
    public List<User> list() {
        return null;
    }

    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public User newInstance(String name) {
        return null;
    }

    @Override
    public User getUserById(int userId) {
        return null;
    }
    
    @Override
    public User getUserById(String userId) {
        Document first = mongoClient.getDatabase("expense").getCollection("users").find(new Document("_id", new ObjectId(userId))).first();
        return new UserImpl(first.getObjectId("_id").toHexString(), first.getString("name"));
    }

    @Override
    public List<ExpenseRequest> findExpenseRequestsByUserId(int userId) {
        return null;
    }

    @Override
    public ExpenseRequest newExpenseRequest(User user) {
        return null;
    }

    @Override
    public ExpenseRequest getExpenseRequestsById(int expenseId) {
        return null;
    }

    @Override
    public ExpenseRequest updateExpenseRequest(ExpenseRequest expenseRequest) {
        return null;
    }

    @Override
    public List<ExpenseItem> findExpenseItemsByRequestId(int requestId) {
        return null;
    }

    @Override
    public ExpenseItem getExpenseItemById(int itemId) {
        return null;
    }

    @Override
    public ExpenseItem newExpenseItem() {
        return null;
    }

    @Override
    public ExpenseItem createExpenseItem(ExpenseItem expenseItem) {
        return null;
    }

    @Override
    public ExpenseItem updateExpenseItem(ExpenseItem newInstance) {
        return null;
    }

    @Override
    public Payment getPaymentByRequestId(int expenseRequestId) {
        return null;
    }

    @Override
    public Payment newPayment(int expenseRequestId) {
        return null;
    }

    @Override
    public Payment createPayment(Payment newInstance) {
        return null;
    }
}
