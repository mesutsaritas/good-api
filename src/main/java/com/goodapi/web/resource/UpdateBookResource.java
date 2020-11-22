package com.goodapi.web.resource;

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
public class UpdateBookResource extends RepresentationModel<BookResource> {

    @NotNull(message = "Id is mandatory")
    private Long Id;

    private String name;

    private String code;

    private Double price;

    private Integer stock;

    private String description;

}
