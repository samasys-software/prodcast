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
        newcustregdetails.setFirstname(rs.getString("firstname"));
        newcustregdetails.setLastname(rs.getString("lastname"));
        newcustregdetails.setEmail(rs.getString("outlet_email_id"));
        newcustregdetails.setWorkphone(rs.getString("workphone"));
        newcustregdetails.setCellphone(rs.getString("cellphone"));
        newcustregdetails.setAddress1(rs.getString("address_1"));
        newcustregdetails.setAddress2(rs.getString("address_2"));
        newcustregdetails.setAddress3(rs.getString("address_3"));
        newcustregdetails.setCity(rs.getString("city"));
        newcustregdetails.setState(rs.getString("state"));
        newcustregdetails.setCountry(rs.getString("country_id"));
        newcustregdetails.setPostalcode(rs.getString("postal_code"));
        newcustregdetails.setSmsallowed(rs.getString("sms_mobile_Y_N"));
       // newcustregdetails.setAccessId(rs.getLong("access_id"));





        return newcustregdetails;
    }

}
