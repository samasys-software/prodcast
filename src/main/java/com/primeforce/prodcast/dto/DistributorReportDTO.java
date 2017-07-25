package com.primeforce.prodcast.dto;

/**
 * Created by Nandhini on 8/24/2016.
 */
public class DistributorReportDTO extends ProdcastDTO{
    private String header,attributes,exportHeader,exportAttributes,reportName;
    private Object result;

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
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

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
