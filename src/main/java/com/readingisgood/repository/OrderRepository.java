package com.readingisgood.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.readingisgood.model.Order;

/**
 * @author msaritas
 *
 */
@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findByCustomerId(@Param("customerId") Long customerId);

}
