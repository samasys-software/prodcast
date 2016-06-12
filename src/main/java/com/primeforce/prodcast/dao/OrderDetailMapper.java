package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.OrderEntry;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sarathan732 on 4/29/2016.
 */
public class OrderDetailMapper implements RowMapper<OrderEntry> {

    public OrderEntry mapRow(ResultSet rs, int rowCount) throws SQLException{
        OrderEntry oe = new OrderEntry();
        oe.setAmount( rs.getFloat("amount"));
        oe.setProductId( rs.getLong( "product_id"));
        oe.setUnitPrice( rs.getFloat("unitprice"));
        oe.setQuantity( rs.getInt("quantity"));
        oe.setProductName( rs.getString("product_name"));

        return oe;
    }
}
