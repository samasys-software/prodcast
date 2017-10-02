package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.Collection;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sarathan732 on 4/29/2016.
 */
public class CompanySettingMapper implements RowMapper<CompanySetting> {

    public CompanySetting mapRow( ResultSet rs , int rowCount ) throws SQLException{
        CompanySetting setting = new CompanySetting();
        setting.setCompanyName(rs.getString("companyname"));
        setting.setSalesTaxRate(rs.getFloat("salestaxrate"));
        setting.setAddress(rs.getString("address"));
        setting.setCity(rs.getString("city"));
        setting.setStateorprovince(rs.getString("stateorprovince"));
        setting.setPostalcode(rs.getString("postalcode"));
        setting.setCountry(rs.getString("country_id"));
        setting.setTimezone(rs.getString("timezone"));
        setting.setPhoneNumber(rs.getString("phonenumber"));
        setting.setFaxNumber(rs.getString("faxnumber"));
        setting.setFulfillmentType(rs.getString("fulfillment_type"));
        setting.setMinimumDeliveryAmount(rs.getFloat("min_delivery_amount"));
        return setting;
    }
}
