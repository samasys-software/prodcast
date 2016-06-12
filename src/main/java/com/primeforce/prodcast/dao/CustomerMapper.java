package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.Customer;
import com.primeforce.prodcast.businessobjects.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sarathan732 on 4/22/2016.
 */
public class CustomerMapper implements RowMapper<Customer> {

    private final boolean miniData ;

    public CustomerMapper(boolean miniData){
        this.miniData = miniData;
    }

    public CustomerMapper(){
        this.miniData = true;
    }
    public Customer mapRow(ResultSet rs, int rowNum ) throws SQLException{

        Customer cust = new Customer();
        cust.setId( rs.getLong( "outlet_id"));
        cust.setCustomerName(rs.getString("outlet_name"));
        if( !miniData ) {
            cust.setUnitNumber(rs.getString("landmark"));
            cust.setBillingAddress1(rs.getString("address_1"));
            cust.setBillingAddress2(rs.getString("address_2"));
            cust.setBillingAddress3(rs.getString("address_3"));
            cust.setCity(rs.getString("city"));
            cust.setCountry(rs.getString("country_id"));
            cust.setPostalCode(rs.getString("postal_code"));
            cust.setState(rs.getString("state"));

            cust.setCustomerType( rs.getString("outlet_type"));

            cust.setFirstname( rs.getString("firstname"));
            cust.setLastname( rs.getString("lastname"));

            cust.setPhonenumber( rs.getString("workphone"));
            cust.setCellPhone(rs.getString("cellphone"));
            
            cust.setArea(rs.getString("area"));
            cust.setNotes( rs.getString("comments"));
            cust.setEmailaddress( rs.getString( "outlet_email_id"));
            cust.setWeekday( rs.getString( "week_day"));

            cust.setArea(rs.getString("area"));
        }
        return cust;
    }

}
