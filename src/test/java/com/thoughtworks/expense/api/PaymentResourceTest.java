package com.thoughtworks.expense.api;

import org.junit.Test;

import javax.ws.rs.core.Response;

import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PaymentResourceTest extends TestBase {
    private String basePath = "/users/1/expense-requests/1/payment";
    private Payment payment;

    @Override
    public void setUp() throws Exception {
        payment = mock(Payment.class);
        when(userRepository.getPaymentByRequestId(1)).thenReturn(payment);
        super.setUp();
    }
    
    @Test
    public void should_get_payment() {
        when(payment.getAccount()).thenReturn("some account");
        when(payment.getAmount()).thenReturn(1000.00);

        Response response = target(basePath).request().get();

        assertThat(response.getStatus(), is(200));

        Map payment = response.readEntity(Map.class);

        assertThat((String) payment.get("uri"), is(basePath));
        assertThat((Double) payment.get("amount"), is(1000.00));
        assertThat((String) payment.get("account"), is("some account"));

    }
}
