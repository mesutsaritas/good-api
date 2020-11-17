package com.readingisgood.model;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "CUSTOMER")
@SequenceGenerator(name = "default_gen", sequenceName = "SEQ_CUSTOMER", allocationSize = 1)
@Getter
@Setter
public class Customer extends Auditable<String, Long> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "USER_NAME")
    private String userName;

    @Basic(optional = false)
    @Column(name = "EMAIL")
    private String email;

    @Basic(optional = false)
    @Column(name = "FIRST_NAME")
    private String firstName;

    @Basic(optional = false)
    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "MOBILE_PHONE_NUMBER")
    private String mobilePhoneNumber;

    @Column(name = "NATIONAL_ID_NUMBER")
    private String nationalIdNumber;

    @Column(name = "ADDRESS")
    private String address;

    @Basic(optional = true)
    @Column(name = "PASSWORD")
    private String password;

    @Basic(optional = true)
    @Column(name = "SALT")
    private String salt;

    @Column(name = "IS_DELETED")
    private Boolean isDeleted = false;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Order> orderList;

}
