package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.ProductFlavors;
import com.primeforce.prodcast.businessobjects.ProductOptions;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductFlavorsMapper implements RowMapper<ProductFlavors> {

    public ProductFlavors mapRow(ResultSet rs, int rowNum ) throws SQLException {

        ProductFlavors productFlavors = new ProductFlavors();
        productFlavors.setFlavorId(rs.getString("product_flavor_id"));
        productFlavors.setFlavorValue(rs.getString("flavor_value"));
        productFlavors.setActive( rs.getBoolean("flavor_active"));
        productFlavors.setProductId(rs.getLong("product_id"));

        return productFlavors;
    }
}