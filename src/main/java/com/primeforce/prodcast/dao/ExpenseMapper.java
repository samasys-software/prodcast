package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.Expense;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sarathan732 on 8/6/2016.
 */
public class ExpenseMapper implements RowMapper<Expense> {


    public Expense mapRow(ResultSet rs, int rowNo)throws SQLException{
        Expense expense = new Expense();
        expense.setId( rs.getLong( "id"));
        expense.setExpenseDate( rs.getDate("exp_date").toString() );
        expense.setAccount( rs.getString("account"));
        expense.setExpenseCategory(rs.getString("catg_desc"));
        expense.setCategoryId(rs.getString("catg_id"));
        expense.setExpenseAmount(rs.getFloat("amount"));
        expense.setDescription( rs.getString("desc1"));
        expense.setDescription2(rs.getString("desc2"));
        expense.setPayMode(rs.getString("pay_mode"));
        return expense;
    }
}
