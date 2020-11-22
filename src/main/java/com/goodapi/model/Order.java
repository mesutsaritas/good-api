package com.goodapi.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author msaritas
 *
 */

@Entity
@Table(name = "ORDERS")
@SequenceGenerator(name = "default_gen", sequenceName = "SEQ_ORDER", allocationSize = 1)
@Getter
@Setter
public class Order extends Auditable<String, Long> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "ORDER_CODE")
    private String orderCode;

    @Basic(optional = false)
    @Column(name = "ORDER_DATE")
    private Date orderDate;

    @Basic(optional = false)
    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "SHIPPING_PRICE")
    private Double shippingPrice;

    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderBook> entries;

}
