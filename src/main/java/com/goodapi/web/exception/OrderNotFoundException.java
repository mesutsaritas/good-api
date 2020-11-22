package com.goodapi.web.exception;

import com.goodapi.web.resource.ErrorResource;

/**
 * @author msaritas
 *
 */
public class OrderNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    private final ErrorResource errorResource;

    public OrderNotFoundException() {
        this.errorResource = new ErrorResource("order_not_found", "Order is not found!");
    }

    public ErrorResource getErrorResource() {
        return errorResource;
    }
}
