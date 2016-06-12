package com.primeforce.prodcast.dto;

import com.primeforce.prodcast.businessobjects.Customer;

import java.util.List;

/**
 * Created by sarathan732 on 4/23/2016.
 */
public class CustomerListDTO extends ProdcastDTO {

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    private List<Customer> customerList;
}
