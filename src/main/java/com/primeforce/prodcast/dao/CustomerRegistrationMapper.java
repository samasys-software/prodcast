package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.CustomerRegistration;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Hai on 11/3/2016.
 */
public class CustomerRegistrationMapper implements RowMapper<CustomerRegistration> {
    public CustomerRegistrationMapper() {

    }

    public CustomerRegistration mapRow(ResultSet rs, int rowNum) throws SQLException {
        CustomerRegistration register = new CustomerRegistration();


        register.setCountry(rs.getString("country_id"));
        register.setConfirmationId(rs.getLong("access_id"));
        register.setMobilePhone(rs.getString("username"));


        return register;
    }

}

