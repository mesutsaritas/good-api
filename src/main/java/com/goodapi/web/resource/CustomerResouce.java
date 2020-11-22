package com.goodapi.web.resource;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author msaritas
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerResouce extends RepresentationModel<CustomerResouce> {

    private Long Id;

    @NotNull
    @NotBlank(message = "UserName is mandatory")
    private String userName;

    @Email(message = "Wrong email address")
    private String email;

    @NotNull
    @NotBlank(message = "FirstName is mandatory")
    private String firstName;

    @NotNull
    @NotBlank(message = "LastName is mandatory")
    private String lastName;

    @Pattern(regexp = "(^$|[0-9]{10})")
    private String mobilePhoneNumber;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank(message = "Password is mandatory")
    private String password;

}
