package com.primeforce.prodcast.businessobjects;

/**
 * Created by Nandhini on 8/23/2016.
 */
public class ReportType {
       private String reportName,reportSql,reportMapper,header,attributes,exportHeader,exportAttributes,condition,groupedBy;
       long reportId;
        public long getReportId() {
            return reportId;
        }

        public void setReportId(long reportId) {
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

        public String getExportHeader() {
        return exportHeader;
    }

        public void setExportHeader(String exportHeader) {
        this.exportHeader = exportHeader;
    }

        public String getExportAttributes() {
        return exportAttributes;
    }

        public void setExportAttributes(String exportAttributes) {
        this.exportAttributes = exportAttributes;
    }

        public String getCondition() { return condition; }

        public void setCondition(String condition) {  this.condition = condition; }

    public String getGroupedBy() {
        return groupedBy;
    }

    public void setGroupedBy(String groupedBy) {
        this.groupedBy = groupedBy;
    }
}
