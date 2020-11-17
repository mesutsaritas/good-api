package com.readingisgood.web.exception;

import com.readingisgood.web.resource.ErrorResource;

/**
 * @author msaritas
 *
 */
public class BookCodeExistException extends Exception {

    private static final long serialVersionUID = 1L;

    private final ErrorResource errorResource;

    public BookCodeExistException() {
        this.errorResource = new ErrorResource("book_code_exist", "book_code_exist!");
    }

    public ErrorResource getErrorResource() {
        return errorResource;
    }

}
