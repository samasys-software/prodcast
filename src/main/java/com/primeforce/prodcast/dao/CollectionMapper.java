package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.Collection;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sarathan732 on 4/29/2016.
 */
public class CollectionMapper implements RowMapper<Collection> {

    public Collection mapRow( ResultSet rs , int rowCount ) throws SQLException{
        Collection collection = new Collection();

        collection.setBillId( rs.getLong( "bill_no"));
        collection.setAmountPaid( rs.getFloat("amount_paid"));
        collection.setEmployeeId( rs.getLong("emp_id"));
        collection.setEmployeeName( rs.getString("firstname") +" " + rs.getString(("lastname")));
        collection.setPaymentType( rs.getString("payment_type"));
        collection.setPaymentDate( rs.getDate("payment_date"));
        collection.setCustomerName( rs.getString("outlet_name"));
        return collection;
    }
}
