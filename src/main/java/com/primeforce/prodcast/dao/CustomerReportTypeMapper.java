package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.CustomerReportType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
     * Created by user on 11/9/2016.
 */
public class CustomerReportTypeMapper implements RowMapper<CustomerReportType>
{


        public CustomerReportType    mapRow(ResultSet rs, int rowNum ) throws SQLException {
            CustomerReportType reptype = new CustomerReportType();
            reptype.setReportId(rs.getLong("report_id"));
            reptype.setReportName(rs.getString("report_name"));
            reptype.setReportSql(rs.getString("report_sql"));
            reptype.setReportMapper(rs.getString("mapper_class"));
            reptype.setHeader(rs.getString("header"));
            reptype.setAttributes(rs.getString("attributes"));
            reptype.setCondition(rs.getString("condition"));


            return reptype;
        }
}
