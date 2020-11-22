package com.goodapi.web.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author msaritas
 *
 */
public class TokenExpireException extends AuthenticationException {
    private static final long serialVersionUID = 1L;

    private String code;

    public TokenExpireException() {
        super("token_expire");
        this.code = "token_expire";
    }

    public TokenExpireException(String code) {
        super(code);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
