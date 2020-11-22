package com.goodapi.web.exception;

import com.goodapi.web.resource.ErrorResource;

/**
 * @author msaritas
 *
 */
public class InsufficientStockOfBooksException extends Exception {
    private static final long serialVersionUID = 1L;

    private final ErrorResource errorResource;

    public InsufficientStockOfBooksException() {
        this.errorResource = new ErrorResource("insufficient stock of books", "Insufficient stock of books!");
    }

    public ErrorResource getErrorResource() {
        return errorResource;
    }
}
