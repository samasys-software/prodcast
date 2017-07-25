package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.Confirmation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Hai on 10/31/2016.
 */
public class ConfirmationMapper implements RowMapper<Confirmation> {
    public Confirmation mapRow(ResultSet rs , int rowCount ) throws SQLException{
        Confirmation confirmation = new Confirmation();

        confirmation.setAccessId(rs.getLong("access_id"));
        confirmation.setUsername(rs.getString("username"));
        confirmation.setCountryId(rs.getString("country_id"));
        confirmation.setStatus(rs.getString("status"));
        confirmation.setIsdCode(rs.getString("isd_code"));

        return confirmation;
    }
}
