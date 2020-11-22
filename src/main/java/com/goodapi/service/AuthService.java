package com.goodapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goodapi.model.Customer;

/**
 * @author msaritas
 *
 */
@Service
public class AuthService {

    private final CustomerService customerService;

    @Autowired
    public AuthService(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * 
     * @param userName
     * @return
     */
    public Customer getCustomerByUserName(String userName) {
        return customerService.findByUserNameAndIsDeletedFalse(userName);
    }

}
