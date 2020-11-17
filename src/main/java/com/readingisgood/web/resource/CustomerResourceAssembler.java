package com.readingisgood.web.resource;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import com.readingisgood.model.Customer;
import com.readingisgood.web.controller.CustomerController;

/**
 * @author msaritas
 *
 */
public class CustomerResourceAssembler extends RepresentationModelAssemblerSupport<Customer, CustomerResouce> {
    /**
     * @param controllerClass
     * @param resourceType
     */
    public CustomerResourceAssembler() {
        super(CustomerController.class, CustomerResouce.class);
    }

    @Override
    public CustomerResouce toModel(Customer entity) {
        CustomerResouce customerResouce = new CustomerResouce();
        customerResouce.setEmail(entity.getEmail());
        customerResouce.setFirstName(entity.getFirstName());
        customerResouce.setLastName(entity.getLastName());
        customerResouce.setMobilePhoneNumber(entity.getMobilePhoneNumber());
        customerResouce.setId(entity.getId());
        customerResouce.setUserName(entity.getUserName());
        customerResouce.add(WebMvcLinkBuilder.linkTo(CustomerController.class).slash(entity.getId()).withSelfRel());
        return customerResouce;
    }

}
