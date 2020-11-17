package com.readingisgood.web.exception;

import com.readingisgood.web.resource.ErrorResource;

/**
 * @author msaritas
 *
 */
public class CustomerNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    private final ErrorResource errorResource;

    public CustomerNotFoundException() {
        this.errorResource = new ErrorResource("customer_not_found", "Customer is not found!");
    }

    public ErrorResource getErrorResource() {
        return errorResource;
    }
}
