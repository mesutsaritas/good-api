package com.goodapi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goodapi.model.Book;
import com.goodapi.repository.BookRepository;
import com.goodapi.web.exception.BookCodeExistException;
import com.goodapi.web.exception.BookNotFoundException;
import com.goodapi.web.resource.BookResource;
import com.goodapi.web.resource.UpdateBookResource;

/**
 * @author msaritas
 *
 */
@Service
public class BookService {

    private static Logger LOGGER = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * 
     * @return
     */
    public List<Book> list() {
        List<Book> result = StreamSupport.stream(bookRepository.findAll().spliterator(), false).collect(Collectors.toList());
        return result;

    }

    /**
     * 
     * @param objId
     * @return
     * @throws BookNotFoundException
     */
    public Book load(Long objId) throws BookNotFoundException {
        Book book = bookRepository.findById(objId).orElseThrow(() -> new BookNotFoundException());
        return book;
    }

    /**
     * 
     * @param bookResource
     * @return
     * @throws BookCodeExistException
     */
    public Book create(BookResource bookResource) throws BookCodeExistException {
        Book bookFromDB = bookRepository.findByCode(bookResource.getCode());
        if (bookFromDB != null) {
            throw new BookCodeExistException();
        }

        Book book = new Book();
        book.setCode(bookResource.getCode());
        book.setDescription(bookResource.getDescription());
        book.setName(bookResource.getName());
        book.setPrice(bookResource.getPrice());
        book.setStock(bookResource.getStock());
        book = bookRepository.save(book);
        LOGGER.info("[BookService][create][A new Book has been created! bookId:{}]", book.getId());
        return book;
    }

    /**
     * 
     * @param bookResource
     * @return
     * @throws BookCodeExistException
     */
    public Book patch(UpdateBookResource bookResource) throws BookNotFoundException {
        Optional<Book> bookFromDB = bookRepository.findById(bookResource.getId());

        return bookFromDB.map(book -> {
            if (bookResource.getDescription() != null) {
                book.setDescription(bookResource.getDescription());
            }
            if (bookResource.getCode() != null) {
                book.setCode(bookResource.getCode());
            }
            if (bookResource.getPrice() != null) {
                book.setPrice(bookResource.getPrice());
            }
            if (bookResource.getName() != null) {
                book.setName(bookResource.getName());
            }
            if (bookResource.getStock() != null) {
                book.setStock(bookResource.getStock());
            }
            bookRepository.save(book);
            LOGGER.info("[BookService][update][Book Updated! customerId:{}]", book.getId());
            return book;
        }).orElseThrow(BookNotFoundException::new);

    }

    /**
     * 
     * @param bookResource
     * @return
     * @throws BookNotFoundException
     */
    public Book update(UpdateBookResource bookResource) throws BookNotFoundException {
        Optional<Book> bookFromDB = bookRepository.findById(bookResource.getId());

        return bookFromDB.map(book -> {
            book.setDescription(bookResource.getDescription());
            book.setCode(bookResource.getCode());
            book.setPrice(bookResource.getPrice());
            book.setName(bookResource.getName());
            book.setStock(bookResource.getStock());
            bookRepository.save(book);
            LOGGER.info("[BookService][update][Book Updated! customerId:{}]", book.getId());
            return book;
        }).orElseThrow(BookNotFoundException::new);

    }

    /**
     * 
     * @param id
     * @throws BookNotFoundException
     */
    public void remove(Long id) throws BookNotFoundException {
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        bookRepository.delete(book);
        LOGGER.info("[BookService][remove][Book Deleted! bookId:{}]", id);
    }

}
