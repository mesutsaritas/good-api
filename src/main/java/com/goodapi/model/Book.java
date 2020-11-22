package com.goodapi.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author msaritas
 *
 */

@Entity
@Table(name = "BOOK")
@SequenceGenerator(name = "default_gen", sequenceName = "SEQ_BOOK", allocationSize = 1)
@Getter
@Setter
public class Book extends Auditable<String, Long> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CODE")
    private String code;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRICE")
    private Double price;

    @Basic(optional = false)
    @Column(name = "STOCK")
    private Integer stock;

}
