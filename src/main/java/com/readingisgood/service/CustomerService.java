package com.readingisgood.service;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.readingisgood.enums.PasswordHashUtil;
import com.readingisgood.model.Customer;
import com.readingisgood.repository.CustomerRepository;
import com.readingisgood.web.exception.CustomerNotFoundException;
import com.readingisgood.web.exception.UserNameAlreadyException;
import com.readingisgood.web.resource.CustomerResouce;
import com.readingisgood.web.resource.CustomerUpdateResouce;

/**
 * @author msaritas
 *
 */
@Service
public class CustomerService {

    private static Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * 
     * @param objId
     * @return
     * @throws CustomerNotFoundException
     */
    public Customer load(Long objId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findByIdAndIsDeletedFalse(objId)
                .orElseThrow(() -> new CustomerNotFoundException());
        return customer;
    }

    /**
     * 
     * @return
     */
    public List<Customer> list() {
        List<Customer> result = StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return result;

    }

    /**
     * 
     * @param customerResource
     * @return
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws UserNameAlreadyException
     */
    public Customer create(CustomerResouce customerResource)
            throws NoSuchAlgorithmException, NoSuchProviderException, UserNameAlreadyException {
        /**
         * Check Username unique From DB
         */
        Customer customerFromDB = customerRepository.findByUserNameAndIsDeletedFalse(customerResource.getUserName());
        if (customerFromDB != null) {
            throw new UserNameAlreadyException();
        }
        Customer customer = new Customer();
        customer.setFirstName(customerResource.getFirstName());
        customer.setLastName(customerResource.getLastName());
        customer.setEmail(customerResource.getEmail());
        customer.setMobilePhoneNumber(customerResource.getMobilePhoneNumber());
        String salt = PasswordHashUtil.INSTANCE.getSalt();
        String securedPassword = PasswordHashUtil.INSTANCE.getSecurePassword(customerResource.getPassword(), salt);
        customer.setPassword(securedPassword);
        customer.setUserName(customerResource.getUserName());
        customer.setCreatedDate(new Date());
        customer = customerRepository.save(customer);
        LOGGER.info("[CustomerService][create][A new customer has been created! customerId:{}]", customer.getId());
        return customer;
    }

    /**
     * 
     * @param customerResource
     * @return
     * @throws CustomerNotFoundException
     */

    public Customer update(CustomerUpdateResouce customerResource) throws CustomerNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findById(customerResource.getId());
        return customerOptional.map(customer -> {
            customer.setUserName(customerResource.getUserName());
            customer.setEmail(customerResource.getEmail());
            customer.setFirstName(customerResource.getFirstName());
            customer.setLastName(customerResource.getLastName());
            customer.setMobilePhoneNumber(customerResource.getMobilePhoneNumber());
            customerRepository.save(customer);
            LOGGER.info("[CustomerService][update][Customer Updated! customerId:{}]", customer.getId());
            return customer;
        }).orElseThrow(CustomerNotFoundException::new);

    }

    /**
     * 
     * @param id
     * @throws CustomerNotFoundException
     */
    public void remove(Long id) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(id).orElseThrow(CustomerNotFoundException::new);
        customer.setIsDeleted(true);
        customerRepository.save(customer);
        LOGGER.info("[CustomerService][remove][Customer Deleted! customerId:{}]", id);
    }

    /**
     * 
     * @param userName
     * @return
     */
    public Customer findByUserNameAndIsDeletedFalse(String userName) {
        return customerRepository.findByUserNameAndIsDeletedFalse(userName);

    }

}
