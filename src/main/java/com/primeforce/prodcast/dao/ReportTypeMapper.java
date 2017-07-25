package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.ReportType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Nandhini on 8/23/2016.
 */
public class ReportTypeMapper implements RowMapper<ReportType> {
public ReportType    mapRow(ResultSet rs, int rowNum ) throws SQLException {
    ReportType reptype = new ReportType();
    reptype.setReportId(rs.getLong("report_id"));
    reptype.setReportName(rs.getString("report_name"));
    reptype.setReportSql(rs.getString("report_sql"));
    reptype.setReportMapper(rs.getString("mapper_class"));
    reptype.setHeader(rs.getString("header"));
    reptype.setAttributes(rs.getString("attributes"));
    reptype.setExportHeader(rs.getString("export_header"));
    reptype.setExportAttributes(rs.getString("export_attributes"));
    reptype.setCondition(rs.getString("condition"));
    reptype.setGroupedBy(rs.getString("grouped_by"));


    return reptype;
    }

}
