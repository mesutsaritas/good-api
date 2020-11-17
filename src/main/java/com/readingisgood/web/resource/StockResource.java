package com.readingisgood.web.resource;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

/**
 * @author msaritas
 *
 */
@Setter
@Getter
public class StockResource extends RepresentationModel<StockResource> {

    private Long Id;

    private Integer amount;

    private Long bookId;

}
