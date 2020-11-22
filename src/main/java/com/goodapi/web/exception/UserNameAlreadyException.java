package com.goodapi.web.exception;

import com.goodapi.web.resource.ErrorResource;

/**
 * @author msaritas
 *
 */
public class UserNameAlreadyException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 2407054235348214466L;

    private final ErrorResource resource;

    public UserNameAlreadyException() {
        resource = new ErrorResource("user_name_already", "user_name_already");
    }

    public ErrorResource getResource() {
        return resource;
    }

}
