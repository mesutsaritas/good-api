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

@Setter
@Getter
public class BookResource extends RepresentationModel<BookResource> {

    private Long Id;

    @NotNull(message = "Name is mandatory")
    private String name;

    @NotNull
    @NotBlank(message = "Code is mandatory")
    private String code;

    @NotNull(message = "Price is mandatory")
    private Double price;

    @NotNull(message = "Stock is mandatory")
    private Integer stock;

    private String description;

}
