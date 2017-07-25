package com.primeforce.prodcast.businessobjects;

/**
 * Created by Hai on 11/10/2016.
 */

public class CustomerReportType {

    private String reportName,reportSql,reportMapper,header,attributes,condition;
    long reportId;
    public long getReportId()
    {
        return reportId;
    }

    public void setReportId(long reportId)
    {
        this.reportId = reportId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportSql() {
        return reportSql;
    }

    public void setReportSql(String reportSql) {
        this.reportSql = reportSql;
    }

    public String getReportMapper() { return reportMapper;        }

    public void setReportMapper(String reportMapper) {
        this.reportMapper = reportMapper;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }


    public String getCondition() { return condition; }

    public void setCondition(String condition) {  this.condition = condition; }
}
