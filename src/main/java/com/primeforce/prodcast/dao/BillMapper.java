package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.Bill;
import com.primeforce.prodcast.businessobjects.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sarathan732 on 4/26/2016.
 */
public class BillMapper implements RowMapper<Bill> {

    boolean mini = true;

    public BillMapper(boolean mini) {
        this.mini = mini;
    }

    public BillMapper() {

    }

    public Bill mapRow(ResultSet rs, int rowNum) throws SQLException {
        Bill bill = new Bill();

        bill.setBillNumber(rs.getLong("bill_no"));
        bill.setBillAmount(rs.getFloat("total_amt"));
        bill.setCustomerId(rs.getLong("cust_id"));
        bill.setOutstandingBalance(rs.getFloat("outstanding_balance"));
        bill.setBillDate(rs.getDate("bill_date"));
        bill.setOrderStatus(rs.getString("order_status"));
        return bill;
    }
}
