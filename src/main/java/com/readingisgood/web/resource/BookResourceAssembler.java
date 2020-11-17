package com.readingisgood.web.resource;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import com.readingisgood.model.Book;
import com.readingisgood.web.controller.BookController;

/**
 * @author msaritas
 *
 */
public class BookResourceAssembler extends RepresentationModelAssemblerSupport<Book, BookResource> {
    /**
     * @param controllerClass
     * @param resourceType
     */
    public BookResourceAssembler() {
        super(BookController.class, BookResource.class);
    }

    @Override
    public BookResource toModel(Book entity) {
        BookResource bookResource = new BookResource();
        bookResource.setCode(entity.getCode());
        bookResource.setDescription(entity.getDescription());
        bookResource.setName(entity.getName());
        bookResource.setPrice(entity.getPrice());
        bookResource.setId(entity.getId());
        bookResource.setStock(entity.getStock());
        bookResource.add(WebMvcLinkBuilder.linkTo(BookController.class).slash(entity.getId()).withSelfRel());
        return bookResource;
    }

}
