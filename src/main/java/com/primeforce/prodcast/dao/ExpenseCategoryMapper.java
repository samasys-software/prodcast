package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.Area;
import com.primeforce.prodcast.businessobjects.ExpenseCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sarathan732 on 4/26/2016.
 */
public class ExpenseCategoryMapper implements RowMapper<ExpenseCategory> {

    public ExpenseCategoryMapper(){

    }
    public ExpenseCategory mapRow(ResultSet rs, int rowNum ) throws SQLException {
        ExpenseCategory area = new ExpenseCategory();
        area.setCategoryId( rs.getLong("catg_id"));
        area.setCategoryDesc( rs.getString( "catg_desc"));
        return area;
    }

}
