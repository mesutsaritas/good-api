package com.goodapi.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.goodapi.model.Book;
import com.goodapi.model.Customer;
import com.goodapi.model.Order;
import com.goodapi.model.OrderBook;
import com.goodapi.repository.OrderRepository;
import com.goodapi.web.exception.BookNotFoundException;
import com.goodapi.web.exception.CustomerNotFoundException;
import com.goodapi.web.exception.InsufficientStockOfBooksException;
import com.goodapi.web.exception.OrderNotFoundException;
import com.goodapi.web.resource.OrderBookResource;
import com.goodapi.web.resource.OrderResource;
import com.goodapi.web.resource.UpdateBookResource;

/**
 * @author msaritas
 *
 */
@Service
public class OrderService {

    private static Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    private CustomerService customerService;

    private BookService bookService;

    @Autowired
    public OrderService(OrderRepository orderRepository, CustomerService customerService, BookService bookService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.bookService = bookService;
    }

    /**
     * 
     * @param orderResource
     * @return
     * @throws CustomerNotFoundException
     * @throws BookNotFoundException
     * @throws InsufficientStockOfBooksException
     */
    public Order create(OrderResource orderResource)
            throws CustomerNotFoundException, BookNotFoundException, InsufficientStockOfBooksException {
        Order insertOrder = new Order();
        insertOrder.setAddress(orderResource.getAddress());
        insertOrder.setOrderDate(orderResource.getOrderDate());
        insertOrder.setOrderCode(orderResource.getOrderCode());
        insertOrder.setShippingPrice(orderResource.getShippingPrice());

        Customer customerFromDB = customerService.load(orderResource.getCustomerId());
        insertOrder.setCustomer(customerFromDB);

        List<OrderBook> insertOrderBookList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(orderResource.getOrderBookResources())) {
            for (OrderBookResource orderBookResource : orderResource.getOrderBookResources()) {
                if (orderBookResource.getId() != null) {
                    Book bookFromDB = bookService.load(orderBookResource.getId());
                    if (bookFromDB.getStock() - orderBookResource.getAmount() < 0) {
                        LOGGER.info(
                                "[OrderService][create][Insufficient stock of books! bookId:{} stockOfTheBook:{} numberOfBookSalesEntered:{} ]",
                                orderBookResource.getId(), bookFromDB.getStock(), orderBookResource.getAmount());
                        throw new InsufficientStockOfBooksException();
                    } else {

                        Integer finallyStock = bookFromDB.getStock() - orderBookResource.getAmount();
                        UpdateBookResource updateBookResource = new UpdateBookResource();
                        updateBookResource.setId(orderBookResource.getId());
                        updateBookResource.setStock(finallyStock);
                        bookService.update(updateBookResource);
                    }

                    OrderBook orderBook = new OrderBook();
                    orderBook.setBook(bookFromDB);
                    orderBook.setAmount(orderBookResource.getAmount());
                    orderBook.setOrder(insertOrder);
                    orderBook.setUnitPrice(bookFromDB.getPrice());
                    insertOrderBookList.add(orderBook);
                }
            }
        }
        insertOrder.setEntries(insertOrderBookList);

        insertOrder = orderRepository.save(insertOrder);
        LOGGER.info("[OrderService][create][A new Order has been created! orderId:{}]", insertOrder.getId());
        return insertOrder;
    }

    /**
     * 
     * @param customerId
     * @return
     */
    public List<Order> customerAllOrders(Long customerId) {
        List<Order> customerOfOrders = orderRepository.findByCustomerId(customerId);
        return customerOfOrders;
    }

    /**
     * @param id
     * @return
     * @throws OrderNotFoundException
     */
    public Order load(Long objId) throws OrderNotFoundException {
        Order order = orderRepository.findById(objId).orElseThrow(() -> new OrderNotFoundException());
        return order;
    }

}
