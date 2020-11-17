package com.readingisgood.web.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

    @NotNull
    @NotBlank(message = "Id of orderBookResources  mandatory")
    private Long Id;

    @NotNull
    @NotBlank(message = "Amount of OrderBookResources  mandatory")
    private Integer amount;

}
