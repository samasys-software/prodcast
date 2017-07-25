package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.EmployeeDetails;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Hai on 11/7/2016.
 */
public class EmployeeDetailsMapper implements RowMapper<EmployeeDetails> {

        public EmployeeDetails mapRow(ResultSet rs, int rowNum ) throws SQLException {
            EmployeeDetails empDetails = new EmployeeDetails();
            empDetails.setCustomerId(rs.getLong("outlet_id"));
            empDetails.setEmployeeId(rs.getLong("employee_id"));
            empDetails.setCustomerType(rs.getString("outlet_type"));
            empDetails.setUserRole(rs.getString("user_role"));
            empDetails.setCustomerName(rs.getString("outlet_name"));
            empDetails.setFirstName(rs.getString("firstname"));
            empDetails.setLastName(rs.getString("lastname"));
            return empDetails;
        }

}
