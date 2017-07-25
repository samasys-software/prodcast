package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.Country;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Nandhini on 8/18/2016.
 */
public class CountryMapper implements RowMapper<Country> {
    public Country    mapRow(ResultSet rs, int rowNum ) throws SQLException {
        Country country = new Country();
        country.setCountryId(rs.getString("country_id"));
        country.setCountryName(rs.getString("country_name"));
        country.setCurrecyId(rs.getString("currency_id"));
        country.setCurrencyName(rs.getString("currency_name"));
        return country;
    }


}
