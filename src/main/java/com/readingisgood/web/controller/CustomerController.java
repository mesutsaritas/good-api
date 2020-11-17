package com.readingisgood.web.controller;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.readingisgood.service.CustomerService;
import com.readingisgood.web.exception.CustomerNotFoundException;
import com.readingisgood.web.exception.UserNameAlreadyException;
import com.readingisgood.web.resource.CustomerResouce;
import com.readingisgood.web.resource.CustomerResourceAssembler;
import com.readingisgood.web.resource.CustomerUpdateResouce;
import com.readingisgood.web.resource.SuccessResource;

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
@RequestMapping("/customer")
@OpenAPIDefinition(info = @Info(title = "Customer API", version = "1.0"))
public class CustomerController {

    private final CustomerService customerService;

    private final CustomerResourceAssembler customerResourceAssembler;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
        this.customerResourceAssembler = new CustomerResourceAssembler();
    }

    /**
     * Get Customer
     * 
     * @param objId
     * @return
     * @throws CustomerNotFoundException
     */
    @Operation(summary = "Get a customer by id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Found the customer",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerResouce.class)) }),
                    @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content) })
    @GetMapping(value = "/{Id}")
    public ResponseEntity<CustomerResouce> load(@PathVariable Long Id) throws CustomerNotFoundException {
        return ResponseEntity.ok().body(customerResourceAssembler.toModel(customerService.load(Id)));
    }

    /**
     * Get Customer List
     * 
     * @param customerResouce
     * @return
     * @throws CustomerNotFoundException
     */
    @Operation(summary = "List All customer ")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List customer",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResouce.class)) }) })
    @GetMapping
    public ResponseEntity<CollectionModel<CustomerResouce>> list() {
        return ResponseEntity.ok().body(customerResourceAssembler.toCollectionModel(customerService.list()));
    }

    /**
     * Create Customer Via Post Request
     * 
     * @param customerResouce
     * @return
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws UserNameAlreadyException
     */
    @Operation(summary = "Create a customer ")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Create a customer",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResouce.class)) }) })
    @PostMapping
    public ResponseEntity<CustomerResouce> create(@Valid @RequestBody CustomerResouce customerResouce)
            throws NoSuchAlgorithmException, NoSuchProviderException, UserNameAlreadyException {
        customerResouce = customerResourceAssembler.toModel(customerService.create(customerResouce));
        return new ResponseEntity<>(customerResouce, HttpStatus.CREATED);

    }

    /**
     * Update Customer Via Post Request
     * 
     * @param customerResouce
     * @return
     * @throws CustomerNotFoundException
     */

    @Operation(summary = "Update a customer by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Updated the customer",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerResouce.class)) }),
                    @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content) })
    @PutMapping
    public ResponseEntity<CustomerResouce> update(@Valid @RequestBody CustomerUpdateResouce customerResouce)
            throws CustomerNotFoundException {
        return ResponseEntity.ok().body(customerResourceAssembler.toModel(customerService.update(customerResouce)));

    }

    /**
     * 
     * @param Id
     * @return
     * @throws CustomerNotFoundException
     */

    @Operation(summary = "Remove a customer by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Deleted the customer",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerResouce.class)) }),
                    @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content) })
    @DeleteMapping(value = "/{Id}")
    public ResponseEntity<SuccessResource> remove(@PathVariable Long Id) throws CustomerNotFoundException {
        customerService.remove(Id);
        return new ResponseEntity<>(new SuccessResource("customer_deleted", ""), HttpStatus.OK);

    }

}
