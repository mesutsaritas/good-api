package com.readingisgood.web.resource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.util.CollectionUtils;

import com.readingisgood.model.Order;
import com.readingisgood.web.controller.OrderController;

/**
 * @author msaritas
 *
 */
public class OrderResourceAssembler extends RepresentationModelAssemblerSupport<Order, OrderResource> {

    /**
     * @param controllerClass
     * @param resourceType
     */
    public OrderResourceAssembler() {
        super(OrderController.class, OrderResource.class);
    }

    @Override
    public OrderResource toModel(Order entity) {
        OrderResource orderResource = new OrderResource();
        orderResource.setCustomerId(entity.getCustomer().getId());
        orderResource.setAddress(entity.getAddress());
        orderResource.setOrderCode(entity.getOrderCode());
        orderResource.setOrderDate(entity.getOrderDate());

        BigDecimal totalPrice = entity.getEntries().stream().filter(Objects::nonNull)
                .filter(p -> (p.getAmount() != null) && (p.getUnitPrice() != null))
                .map(p -> new BigDecimal(p.getUnitPrice()).multiply(new BigDecimal(p.getAmount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        totalPrice = totalPrice.add(new BigDecimal(entity.getShippingPrice()));
        orderResource.setTotalPrice(totalPrice.doubleValue());
        orderResource.setId(entity.getId());

        orderResource.setShippingPrice(entity.getShippingPrice());

        List<OrderBookResource> orderBookResources = new ArrayList<>();
        if (!CollectionUtils.isEmpty(entity.getEntries())) {
            entity.getEntries().forEach(p -> {
                OrderBookResource bookResource = new OrderBookResource();
                bookResource.setAmount(p.getAmount());
                bookResource.setId(p.getId());
                bookResource.setPrice(p.getUnitPrice());
                orderBookResources.add(bookResource);
            });
        }

        orderResource.setOrderBookResources(orderBookResources);
        orderResource.add(WebMvcLinkBuilder.linkTo(OrderController.class).slash(entity.getId()).withSelfRel());
        return orderResource;
    }

}
