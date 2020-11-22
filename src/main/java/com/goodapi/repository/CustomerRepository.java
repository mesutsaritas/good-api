package com.goodapi.repository;

import java.util.Iterator;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.goodapi.model.Customer;

/**
 * @author msaritas
 *
 */
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Optional<Customer> findByIdAndIsDeletedFalse(Long id);

    Customer findByUserNameAndIsDeletedFalse(String userName);

    Iterator<Customer> findByIsDeletedFalse();

}
