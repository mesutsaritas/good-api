package com.goodapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.goodapi.model.Book;

/**
 * @author msaritas
 *
 */
@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    /**
     * @param code
     * @return
     */
    Book findByCode(String code);

}
