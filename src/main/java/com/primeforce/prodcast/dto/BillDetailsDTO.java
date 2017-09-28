package com.primeforce.prodcast.dto;

import com.primeforce.prodcast.businessobjects.Order;
import org.apache.poi.ss.formula.functions.T;

public class BillDetailsDTO<T> extends ProdcastDTO{
    private Order order;
    private T outstandingBills;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }


    public T getOutstandingBills() {
        return outstandingBills;
    }

    public void setOutstandingBills(T outstandingBills) {
        this.outstandingBills = outstandingBills;
    }
}
