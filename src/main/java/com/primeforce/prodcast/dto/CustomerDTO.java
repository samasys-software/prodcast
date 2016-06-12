package com.primeforce.prodcast.dto;

import com.primeforce.prodcast.businessobjects.Customer;

/**
 * Created by sarathan732 on 4/26/2016.
 */
public class CustomerDTO extends ProdcastDTO{
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    private Customer customer;
}
