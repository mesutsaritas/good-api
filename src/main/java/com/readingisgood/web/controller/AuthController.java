package com.readingisgood.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.readingisgood.enums.PasswordHashUtil;
import com.readingisgood.model.Customer;
import com.readingisgood.security.JwtUtil;
import com.readingisgood.service.AuthService;
import com.readingisgood.web.exception.UserNameAndPasswordException;
import com.readingisgood.web.resource.AuthResource;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * @author msaritas
 *
 */

@RestController
@RequestMapping("/auth/oauth")
@OpenAPIDefinition(info = @Info(title = "Authentacion API", version = "1.0"))
public class AuthController {

    private static Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * 
     * @param authResource
     * @return
     * @throws Exception
     */
    @Operation(summary = "Get token ")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Get token",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResource.class)) }) })
    @PostMapping("/token")
    public ResponseEntity<AuthResource> getToken(@RequestBody AuthResource authResource) throws Exception {

        String username = authResource.getUserName();
        String password = authResource.getPassword();
        if (!StringUtils.hasText(authResource.getUserName()) || !StringUtils.hasText(password)) {
            LOGGER.debug("[SecurityController][Login:UsernameorPasswordIsEmpty][UserName:{} Password :{}", username, password);
            throw new UserNameAndPasswordException();
        }

        Customer customer = authService.getCustomerByUserName(username);
        boolean isPasswordCorrect;
        if (customer != null) {
            isPasswordCorrect = PasswordHashUtil.INSTANCE.checkPassword(password, customer.getPassword(), customer.getSalt());
            if (!isPasswordCorrect) {
                throw new UserNameAndPasswordException();
            } else {
                // User is authenticated.
                String token = JwtUtil.INSTANCE.addAuthentication(customer.getId(), customer.getUserName());

                AuthResource resource = new AuthResource();
                resource.setToken(token);
                return ResponseEntity.ok().body(resource);
            }
        } else {
            throw new UserNameAndPasswordException();
        }

    }
}