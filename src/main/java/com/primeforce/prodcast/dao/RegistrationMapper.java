package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.Registration;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Nandhini on 10/25/2016.
 */
public class RegistrationMapper implements RowMapper<Registration> {

    public RegistrationMapper(){

    }
    public Registration mapRow(ResultSet rs, int rowNum ) throws SQLException {
        Registration registration = new Registration();
        registration.setRegistrationId(rs.getLong("registration_id"));
        registration.setFirstName(rs.getString("firstname"));
        registration.setLastName(rs.getString("lastname"));
        registration.setCountry(rs.getString("country"));
        registration.setEmailId(rs.getString("email_id"));
        registration.setCellPhone(rs.getString("cell_phone"));
        registration.setStatus(rs.getString("status"));
        registration.setComments(rs.getString("comments"));
        return registration;
    }

}
