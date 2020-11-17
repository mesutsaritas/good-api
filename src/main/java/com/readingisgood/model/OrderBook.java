package com.readingisgood.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author msaritas
 *
 */
@Entity
@Table(name = "ORDER_BOOK")
@SequenceGenerator(name = "default_gen", sequenceName = "SEQ_ORDER_BOOK", allocationSize = 1)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderBook extends Auditable<String, Long> {

    private static final long serialVersionUID = 1L;

    @JoinColumn(name = "ORDER_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Order order;

    @JoinColumn(name = "BOOK_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Book book;

    @Basic(optional = false)
    @Column(name = "AMOUNT")
    private Integer amount;

    @Basic(optional = false)
    @Column(name = "UNIT_PRICE")
    private Double unitPrice;

}
