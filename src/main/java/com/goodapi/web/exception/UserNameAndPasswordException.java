package com.goodapi.web.exception;

import com.goodapi.web.resource.ErrorResource;

/**
 * @author msaritas
 *
 */
public class UserNameAndPasswordException extends Exception {
    private static final long serialVersionUID = 1L;

    private final ErrorResource errorResource;

    public UserNameAndPasswordException() {
        this.errorResource = new ErrorResource("UsernameorPasswordWrong", "Username And Passowrd Wrong!");
    }

    public ErrorResource getErrorResource() {
        return errorResource;
    }
}
