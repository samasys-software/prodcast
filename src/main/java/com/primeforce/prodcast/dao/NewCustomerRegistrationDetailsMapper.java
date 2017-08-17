package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.NewCustomerRegistrationDetails;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by SamayuSoftcorp on 16-03-2017.
 */
public class NewCustomerRegistrationDetailsMapper implements RowMapper<NewCustomerRegistrationDetails>{
    public NewCustomerRegistrationDetails mapRow(ResultSet rs, int rowNum ) throws SQLException {
        NewCustomerRegistrationDetails newcustregdetails = new NewCustomerRegistrationDetails();
        newcustregdetails.setFirstName(rs.getString("firstname"));
        newcustregdetails.setLastName(rs.getString("lastname"));
        newcustregdetails.setEmail(rs.getString("outlet_email_id"));
        newcustregdetails.setWorkPhone(rs.getString("workphone"));
        newcustregdetails.setCellPhone(rs.getString("cellphone"));
        newcustregdetails.setAddress1(rs.getString("address_1"));
        newcustregdetails.setAddress2(rs.getString("address_2"));
        newcustregdetails.setAddress3(rs.getString("address_3"));
        newcustregdetails.setCity(rs.getString("city"));
        newcustregdetails.setState(rs.getString("state"));
        newcustregdetails.setCountry(rs.getString("country_id"));
        newcustregdetails.setPostalCode(rs.getString("postal_code"));
        newcustregdetails.setSmsAllowed(rs.getString("sms_mobile_Y_N"));
        newcustregdetails.setCustomerId(rs.getLong("outlet_id"));


        newcustregdetails.setCustomerId(rs.getLong("outlet_id"));



        return newcustregdetails;
    }

}
