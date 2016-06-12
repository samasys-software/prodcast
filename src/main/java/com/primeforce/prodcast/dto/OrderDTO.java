package com.primeforce.prodcast.dto;

import com.primeforce.prodcast.businessobjects.Order;

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
}
