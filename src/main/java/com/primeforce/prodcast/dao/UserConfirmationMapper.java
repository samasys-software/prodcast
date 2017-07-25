package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.UserConfirmation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Thiru on 11/1/2017.
 */
public class UserConfirmationMapper implements RowMapper<UserConfirmation>
{

        public UserConfirmation mapRow(ResultSet rs, int rowCount) throws SQLException {
            UserConfirmation userconfirmation = new UserConfirmation();

            userconfirmation.setPin(rs.getLong("pin"));
            userconfirmation.setMobilePhone(rs.getString("mobile_no"));
            userconfirmation.setCountryId(rs.getString("country_id"));
            userconfirmation.setIsdCode(rs.getString("isd_code"));

            return userconfirmation;
        }
    }