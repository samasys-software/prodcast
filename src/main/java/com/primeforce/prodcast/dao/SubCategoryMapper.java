package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.Category;
import com.primeforce.prodcast.businessobjects.SubCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sarathan732 on 4/26/2016.
 */
public class SubCategoryMapper implements RowMapper<SubCategory> {

    public SubCategoryMapper(){

    }
    public SubCategory mapRow(ResultSet rs, int rowNum ) throws SQLException {
        SubCategory subCategory = new SubCategory();
        subCategory.setCategoryId( rs.getLong("product_catg_id"));
        subCategory.setSubCategoryId( rs.getLong("product_sub_catg_id"));
        subCategory.setSubCategoryName( rs.getString("product_subcag_name"));
        return subCategory;
    }

}
