package com.goodapi.service;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.goodapi.model.Customer;
import com.goodapi.repository.CustomerRepository;

/**
 * @author msaritas
 *
 */
@RunWith(SpringRunner.class)
public class CustomerServiceTests {
    private CustomerService customerServiceSpy;

    @MockBean
    private CustomerRepository customerRepository;

    @Before
    public void setUp() {
        customerServiceSpy = spy(new CustomerService(customerRepository));

    }

    @Test
    public void loadShouldReturnCustomerWithGivenObjIdWhenObjIdNotNull() throws Exception {
        Customer customer = new Customer();
        customer.setId(8L);
        customer.setUserName("Deneme");
        doReturn(customer).when(customerRepository).findByIdAndIsDeletedFalse(customer.getId());
        Customer customer2 = customerServiceSpy.load(customer.getId());
        Assert.assertEquals(customer.getId(), customer2.getId());
        verify(customerServiceSpy, times(1)).load(customer.getId());
    }

}
