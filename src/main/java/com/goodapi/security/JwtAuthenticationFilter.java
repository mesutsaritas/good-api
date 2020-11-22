package com.goodapi.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;

import com.goodapi.model.Customer;
import com.goodapi.repository.CustomerRepository;
import com.goodapi.web.exception.TokenExpireException;

/**
 * @author msaritas
 *
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final CustomerRepository customerRepository;

    public JwtAuthenticationFilter(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith(JwtUtil.INSTANCE.AUTH_HEADER_NAME)) {
            throw new PreAuthenticatedCredentialsNotFoundException("401 - SC_UNAUTHORIZED");
        }

        String authToken = header.substring(JwtUtil.INSTANCE.AUTH_HEADER_NAME.length());
        Long customerId = 0L;
        try {

            customerId = JwtUtil.INSTANCE.parseToken(authToken);
        } catch (Exception e) {
            throw new TokenExpireException();
        }

        // Login olduktan sonra current User ı da Cache almak lazım.
        Customer customer = customerRepository.findById(customerId).get();
        if (customer == null) {
            throw new PreAuthenticatedCredentialsNotFoundException("401 - SC_UNAUTHORIZED : No user found.");
        }

        MDC.put("userName", customer.getUserName());

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(customer.getUserName(),
                customer.getPassword());

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        // SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
        SecurityContextHolder.getContext().setAuthentication(authRequest);
        return authRequest;
    }

}
