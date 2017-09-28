package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sarathan732 on 4/29/2016.
 */
public class OrderMapper implements RowMapper<Order> {



    public Order mapRow(ResultSet rs, int rowNum ) throws SQLException{
        Order order = new Order();
        order.setBillDate( rs.getDate("bill_date"));
        order.setCustomerId( rs.getLong("cust_id"));
        order.setCustomerName( rs.getString("outlet_name"));
        order.setOrderId( rs.getLong("orderdetailid"));
        order.setBillNumber( rs.getLong("bill_no"));
        order.setEmployeeId( rs.getLong("emp_id"));
        order.setEmployeeName ( rs.getString("firstname")+" "+ rs.getString("lastname"));
        order.setTotalAmount( rs.getFloat("total_amt"));
        order.setCreateTime( rs.getTimestamp("enter_dt_tm"));
        order.setOutstandingBalance( rs.getFloat("outstanding_balance"));
        order.setDiscountType( rs.getInt("discount_type"));
        order.setDiscount(rs.getFloat("discount"));

        order.setCustomerEmail( rs.getString("outlet_email_id"));
        order.setEmployeeEmail( rs.getString("email_id"));
        order.setDistributorEmail( rs.getString("dist_email_id"));
        order.setDistributorName(rs.getString("cust_name"));
        order.setCountryCode(rs.getString("isd_code"));
        order.setCellPhone(rs.getString("cellphone"));
        order.setDistributorId(rs.getLong("distributor_id"));
        order.setOrderStatus(rs.getString("order_status"));
        order.setFulfillmentType(rs.getString("shippingmethodid"));
        order.setDeliveryAddress(rs.getString("delivery_address"));
        //order.setEmployeeId( Long.parseLong( rs.getString("user_id")));

        return order;
    }

}
