package com.readingisgood.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.readingisgood.model.Order;
import com.readingisgood.service.OrderService;
import com.readingisgood.web.exception.BookNotFoundException;
import com.readingisgood.web.exception.CustomerNotFoundException;
import com.readingisgood.web.exception.InsufficientStockOfBooksException;
import com.readingisgood.web.exception.OrderNotFoundException;
import com.readingisgood.web.resource.CustomerResouce;
import com.readingisgood.web.resource.OrderResource;
import com.readingisgood.web.resource.OrderResourceAssembler;

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
@RequestMapping("/order")
@OpenAPIDefinition(info = @Info(title = "Reading is Good API", version = "1.0"))
public class OrderController {

    private final OrderService orderService;

    private final OrderResourceAssembler orderResourceAssembler;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
        this.orderResourceAssembler = new OrderResourceAssembler();
    }

    /**
     * 
     * @param Id
     * @return
     * @throws CustomerNotFoundException
     */
    @Operation(summary = "Get a order by id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Found the order",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerResouce.class)) }),
                    @ApiResponse(responseCode = "404", description = "Order not found", content = @Content) })
    @GetMapping(value = "/{Id}")
    public ResponseEntity<OrderResource> load(@PathVariable Long Id) throws OrderNotFoundException {
        return ResponseEntity.ok().body(orderResourceAssembler.toModel(orderService.load(Id)));
    }

    /**
     * 
     * @param orderResource
     * @return
     * @throws CustomerNotFoundException
     * @throws BookNotFoundException
     * @throws InsufficientStockOfBooksException
     */
    @Operation(summary = "Create a order ")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Create a order",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResource.class)) }) })
    @PostMapping
    public ResponseEntity<OrderResource> create(@Valid @RequestBody OrderResource orderResource)
            throws CustomerNotFoundException, BookNotFoundException, InsufficientStockOfBooksException {
        orderResource = orderResourceAssembler.toModel(orderService.create(orderResource));
        return new ResponseEntity<>(orderResource, HttpStatus.CREATED);

    }

    /**
     * 
     * @param customerId
     * @return
     */
    @Operation(summary = "Orders of the customer")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Orders of the customer",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResource.class)) }) })
    @GetMapping(value = "/customerAllOrders/{customerId}")
    public ResponseEntity<CollectionModel<OrderResource>> customerAllOrders(@PathVariable Long customerId) {
        List<Order> orders = orderService.customerAllOrders(customerId);
        return ResponseEntity.ok().body(orderResourceAssembler.toCollectionModel(orders));
    }

}
