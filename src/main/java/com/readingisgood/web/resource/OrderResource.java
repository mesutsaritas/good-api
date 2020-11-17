package com.readingisgood.web.resource;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

/**
 * @author msaritas
 *
 */
@Setter
@Getter
public class OrderResource extends RepresentationModel<OrderResource> {

    private Long Id;

    @NotNull
    @NotBlank(message = "OrderCode is mandatory")
    private String orderCode;

    @NotNull(message = "OrderDate is mandatory")
    private Date orderDate;

    @NotNull
    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotNull(message = "ShippingPrice is mandatory")
    private Double shippingPrice;

    /**
     * amount*unitPrice+shippingPrice
     */
    private Double totalPrice;

    @NotEmpty
    @NotNull(message = "Book List is mandatory")
    private List<OrderBookResource> orderBookResources;

    @NotNull(message = "CustomerId is mandatory")
    private Long customerId;
}
