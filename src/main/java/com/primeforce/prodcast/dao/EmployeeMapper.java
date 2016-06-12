package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sarathan732 on 4/22/2016.
 */
public class EmployeeMapper implements RowMapper<Employee> {

    public Employee mapRow(ResultSet rs, int rowNum ) throws SQLException{

        Employee emp = new Employee();
        emp.setEmployeeId( rs.getLong("employee_id"));
        emp.setFirstname( rs.getString("firstname"));
        emp.setLastname( rs.getString("lastname"));
        emp.setTitle( rs.getString("title"));
        emp.setEmailId(rs.getString("email_id"));
        emp.setCellphone( rs.getString("cellphone"));
        emp.setHomephone(rs.getString("homephone"));
        emp.setWorkphone(rs.getString("workphone"));
        emp.setSalary(rs.getInt("salary"));
        emp.setHireDate( rs.getDate("hire_dt"));
        emp.setTerminationDate( rs.getDate("termination_dt"));
        emp.setAllowance( rs.getInt("allowance"));
        emp.setAddress1( rs.getString("address_1"));
        emp.setAddress2( rs.getString("address_2"));
        emp.setAddress3( rs.getString("address_3"));
        emp.setCity( rs.getString("city"));
        emp.setState( rs.getString("state"));
        emp.setPostalCode(rs.getString("postal_code"));
        emp.setCountryId(rs.getString("country_id"));
        emp.setActive( rs.getBoolean("active_yn"));
        emp.setType( rs.getString("type"));
        emp.setLocation( rs.getString("location"));
        emp.setGender( rs.getString("sex"));
        emp.setDateOfBirth( rs.getDate("dob"));
        emp.setComments( rs.getString("comments"));
        emp.setUserRole( rs.getString("user_role"));
        return emp;
    }

}
