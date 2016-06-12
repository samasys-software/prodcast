package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.Brand;
import com.primeforce.prodcast.businessobjects.Category;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sarathan732 on 4/26/2016.
 */
public class CategoryMapper implements RowMapper<Category> {

    public CategoryMapper(){

    }
    public Category mapRow(ResultSet rs, int rowNum ) throws SQLException {
        Category category = new Category();
        category.setCategoryId( rs.getLong("product_catg_id"));
        category.setCategoryName( rs.getString( "product_cag_name"));
        return category;
    }

}
