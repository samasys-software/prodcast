package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.Brand;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sarathan732 on 4/26/2016.
 */
public class BrandMapper implements RowMapper<Brand> {

    public BrandMapper(){

    }
    public Brand mapRow(ResultSet rs, int rowNum ) throws SQLException {
        Brand brand = new Brand();
        brand.setBrandId( rs.getLong("product_brand_id"));
        brand.setBrandName( rs.getString( "product_brand_name"));
        return brand;
    }

}
