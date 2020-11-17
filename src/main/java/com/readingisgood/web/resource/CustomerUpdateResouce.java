package com.readingisgood.web.resource;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * @author msaritas
 *
 */
@Data
public class CustomerUpdateResouce {

    @NotNull(message = "Id is mandatory")
    private Long Id;

    @NotBlank(message = "UserName is mandatory")
    private String userName;

    @Email(message = "Wrong email address")
    private String email;

    @NotBlank(message = "FirstName is mandatory")
    private String firstName;

    @NotBlank(message = "LastName is mandatory")
    private String lastName;

    @Pattern(regexp = "(^$|[0-9]{10})")
    private String mobilePhoneNumber;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank(message = "Password is mandatory")
    private String password;

}
