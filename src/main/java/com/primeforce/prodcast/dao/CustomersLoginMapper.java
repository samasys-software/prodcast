package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.CustomersLogin;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Hai on 11/1/2016.
 */
public class CustomersLoginMapper implements RowMapper<CustomersLogin> {

        public CustomersLogin mapRow(ResultSet rs, int rowNum ) throws SQLException {
            CustomersLogin customers = new CustomersLogin();
            customers.setAccessId(rs.getLong("access_id"));
            customers.setUsername(rs.getString("username"));
            customers.setCurrencySymbol(rs.getString("currency_symbol"));
            customers.setIsdCode(rs.getString("isd_code"));

            return customers;
        }

}


