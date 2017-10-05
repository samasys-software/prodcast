package com.primeforce.prodcast.dto;

import com.primeforce.prodcast.businessobjects.Bill;
import com.primeforce.prodcast.businessobjects.Customer;
import com.primeforce.prodcast.businessobjects.Order;

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
    private Order bill;

    public Order getBill() {
        return bill;
    }

    public void setBill(Order bill) {
        this.bill = bill;
    }
}
