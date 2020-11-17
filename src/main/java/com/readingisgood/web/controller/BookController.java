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

import com.readingisgood.service.BookService;
import com.readingisgood.web.exception.BookCodeExistException;
import com.readingisgood.web.exception.BookNotFoundException;
import com.readingisgood.web.resource.BookResource;
import com.readingisgood.web.resource.BookResourceAssembler;
import com.readingisgood.web.resource.SuccessResource;
import com.readingisgood.web.resource.UpdateBookResource;

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
@RequestMapping("/book")
@OpenAPIDefinition(info = @Info(title = "Book API", version = "1.0"))
public class BookController {

    private final BookService bookService;

    private final BookResourceAssembler bookResourceAssembler;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
        this.bookResourceAssembler = new BookResourceAssembler();
    }

    /**
     * Get Book
     * 
     * @param objId
     * @return
     * @throws BookNotFoundException
     */
    @Operation(summary = "Get a book by id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Found the book",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BookResource.class)) }),
                    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content) })
    @GetMapping(value = "/{Id}")
    public ResponseEntity<BookResource> load(@PathVariable Long Id) throws BookNotFoundException {
        return ResponseEntity.ok().body(bookResourceAssembler.toModel(bookService.load(Id)));
    }

    /**
     * Get Book List
     * 
     * @return
     */
    @Operation(summary = "List All Book ")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List Books",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = BookResource.class)) }) })
    @GetMapping
    public ResponseEntity<CollectionModel<BookResource>> list() {
        return ResponseEntity.ok().body(bookResourceAssembler.toCollectionModel(bookService.list()));
    }

    /**
     * 
     * @param bookResource
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws BookCodeExistException
     */
    @Operation(summary = "Create a book ")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Create a book",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = BookResource.class)) }) })
    @PostMapping
    public ResponseEntity<BookResource> create(@Valid @RequestBody BookResource bookResource) throws BookCodeExistException {
        bookResource = bookResourceAssembler.toModel(bookService.create(bookResource));
        return new ResponseEntity<>(bookResource, HttpStatus.CREATED);
    }

    /**
     * 
     * @param updateBookResource
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws BookCodeExistException
     * @throws BookNotFoundException
     */
    @Operation(summary = "Update a book ")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Update a book", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateBookResource.class)) }) })
    @PutMapping
    public ResponseEntity<BookResource> update(@Valid @RequestBody UpdateBookResource updateBookResource)
            throws BookNotFoundException {
        BookResource bookResource = bookResourceAssembler.toModel(bookService.update(updateBookResource));
        return new ResponseEntity<>(bookResource, HttpStatus.OK);
    }

    /**
     * 
     * @param Id
     * @return
     * @throws BookNotFoundException
     */
    @Operation(summary = "Remove a book by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Deleted the book",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BookResource.class)) }),
                    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content) })
    @DeleteMapping(value = "/{Id}")
    public ResponseEntity<SuccessResource> remove(@PathVariable Long Id) throws BookNotFoundException {
        bookService.remove(Id);
        return new ResponseEntity<>(new SuccessResource("book_deleted", ""), HttpStatus.OK);

    }

}
