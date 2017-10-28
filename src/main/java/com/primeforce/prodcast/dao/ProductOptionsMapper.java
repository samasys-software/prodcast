package com.primeforce.prodcast.dao;


import com.primeforce.prodcast.businessobjects.ProductOptions;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductOptionsMapper implements RowMapper<ProductOptions> {

    public ProductOptions mapRow(ResultSet rs, int rowNum ) throws SQLException {

        ProductOptions productOptions = new ProductOptions();
        productOptions.setOptionId(rs.getString("product_option_id"));
        productOptions.setOptionValue(rs.getString("option_value"));
        productOptions.setActive( rs.getBoolean("option_active"));
        productOptions.setUnitPrice( rs.getFloat("option_wholesale_price"));
        productOptions.setRetailPrice(rs.getFloat("option_retail_price"));
        productOptions.setProductId(rs.getLong("product_id"));

        return productOptions;
    }
}
