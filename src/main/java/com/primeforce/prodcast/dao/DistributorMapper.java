package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.Area;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sarathan732 on 4/26/2016.
 */
public class DistributorMapper implements RowMapper<Distributor> {

    public DistributorMapper(){

    }
    public Distributor mapRow(ResultSet rs, int rowNum ) throws SQLException {
        Distributor distributor = new Distributor();
        distributor.setDistributorId( rs.getLong("dist_id"));
        distributor.setCompanyName(rs.getString("cust_name"));
        distributor.setType(rs.getString("customer_type"));
        distributor.setFirstName(rs.getString("firstname"));
        distributor.setLastName(rs.getString("lastname"));
        distributor.setTitle( rs.getString("title"));
        distributor.setEmailAddress(rs.getString("email_id"));
        distributor.setCellPhone(rs.getString("cellphone"));
        distributor.setWorkPhone(rs.getString("workphone"));
        distributor.setHomePhone(rs.getString("homephone"));
        distributor.setAddress1(rs.getString("address_1"));
        distributor.setAddress2(rs.getString("address_2"));
        distributor.setAddress3(rs.getString("address_3"));
        distributor.setCity(rs.getString("city"));
        distributor.setState(rs.getString("state"));
        distributor.setCountry(rs.getString("country_id"));
        distributor.setActive(rs.getBoolean("active_yn"));
        distributor.setGender(rs.getString("sex"));
        distributor.setManufacturer(rs.getBoolean("manufacture_yn"));
        distributor.setComments( rs.getString("comments"));
        distributor.setPostalCode(rs.getString("postal code"));
        distributor.setCurrencySymbol( rs.getString("currency_symbol"));
        distributor.setTimezone(rs.getString("timezone"));
        distributor.setOpenToPublic(rs.getBoolean("open_to_public"));
        distributor.setLogo( rs.getString("logo"));

        return distributor;
    }

}
