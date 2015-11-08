package com.thoughtworks.expense.core.impl.sql;

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

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private final SqlSessionFactory sqlSessionFactory;

    public UserRepositoryImpl() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
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
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserImpl user = session.selectOne("UserMapper.selectOne", 1);
            return user;
        } finally {
            session.close();
        }
    }

    @Override
    public User getUserById(String userId) {
        return getUserById(Integer.parseInt(userId));
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
