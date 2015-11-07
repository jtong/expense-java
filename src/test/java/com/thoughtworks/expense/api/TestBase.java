package com.thoughtworks.expense.api;

import com.thoughtworks.expense.core.UserRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import javax.ws.rs.core.Application;

import static org.mockito.Mockito.mock;

public class TestBase extends JerseyTest {
    protected UserRepository userRepository = mock(UserRepository.class);

    @Override
    protected Application configure() {
        return new ResourceConfig().register(new AbstractBinder() {


            @Override
            protected void configure() {

                bind(userRepository).to(UserRepository.class);
            }
        }).packages("com.thoughtworks.expense");
    }
}
