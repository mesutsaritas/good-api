package com.readingisgood.web.resource;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

/**
 * @author msaritas
 *
 */

@Getter
@Setter
public class OrderBookResource extends RepresentationModel<BookResource> {

    /**
     * Order create ederken kullanacağim
     */
    private Long Id;

    private Double price;

    private Integer amount;

}
