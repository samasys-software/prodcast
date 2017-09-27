package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.CustomersLogin;
import com.primeforce.prodcast.businessobjects.Discount;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DiscountMapper implements RowMapper<Discount> {

    public Discount mapRow(ResultSet rs, int rowNum ) throws SQLException {
        Discount discount = new Discount();
        discount.setDiscountType(rs.getInt("discount_type"));
        discount.setDiscountValue(rs.getFloat("discount"));

        return discount;
    }
}
