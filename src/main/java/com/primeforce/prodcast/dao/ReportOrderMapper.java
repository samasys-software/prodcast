package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sarathan732 on 5/1/2016.
 */
public class ReportOrderMapper implements RowMapper<Order> {

    public Order mapRow(ResultSet rs , int rowCount ) throws SQLException{
        Order order = new Order();
        order.setOrderId( rs.getLong( "orderdetailid"));
        order.setBillNumber( rs.getLong("bill_no"));
        order.setBillDate( rs.getDate("bill_date"));
        order.setTotalAmount( rs.getFloat("total_amt"));
        order.setOutstandingBalance( rs.getFloat("outstanding_balance"));
        order.setCustomerName( rs.getString("outlet_name"));
        return order;
    }
}
