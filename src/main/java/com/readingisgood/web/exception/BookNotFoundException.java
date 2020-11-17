package com.readingisgood.web.exception;

import com.readingisgood.web.resource.ErrorResource;

/**
 * @author msaritas
 *
 */
public class BookNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    private final ErrorResource errorResource;

    public BookNotFoundException() {
        this.errorResource = new ErrorResource("book_not_found", "Book is not found!");
    }

    public ErrorResource getErrorResource() {
        return errorResource;
    }
}
