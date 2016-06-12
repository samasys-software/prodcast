package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sarathan732 on 4/25/2016.
 */
public class ProductMapper implements RowMapper<Product> {

    public Product mapRow(ResultSet rs, int rowNum ) throws SQLException{

        //(product_name , product_desc, product_sku, distributor_id ,  manufacturer_id ,  unitprice, price_type , prod_catg_id , prod_sub_catg_id , product_brand_id , active,  user_id , updt_dt_tm , ip_address)


        Product product = new Product();
        product.setId( rs.getLong( "product_id" ));
        product.setProductName( rs.getString( "product_name" ));
        product.setProductDesc( rs.getString("product_desc"));
        product.setProductSku( rs.getString("product_sku"));
        product.setCategoryId( rs.getLong( "prod_catg_id"));
        product.setSubCategoryId( rs.getLong( "product_sub_catg_id"));
        product.setBrandId( rs.getLong( "product_brand_id"));
        product.setActive( rs.getBoolean("active"));
        product.setUnitPrice( rs.getFloat("unitprice"));
        product.setPriceType( rs.getString("price_type"));
        return product;
    }
}
