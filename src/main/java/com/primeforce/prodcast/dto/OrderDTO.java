package com.primeforce.prodcast.dto;

import com.primeforce.prodcast.businessobjects.Bill;
import com.primeforce.prodcast.businessobjects.Order;

import java.util.List;

/**
 * Created by sarathan732 on 4/29/2016.
 */
public class OrderDTO extends ProdcastDTO {

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    private Order order ;

    private List<Bill> outstandingBills;

    public List<Bill> getOutstandingBills() {
        return outstandingBills;
    }

    public void setOutstandingBills(List<Bill> outstandingBills) {
        this.outstandingBills = outstandingBills;
    }
}
