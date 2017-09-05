package com.primeforce.prodcast.dto;

/**
 * Created by Hai on 11/10/2016.
 */
public class CustomerReportDTO  extends ProdcastDTO {



    private String header,attributes,reportName;
    private Object result;
    private float amount,outstandingBalance,amountPaid;
    private String reportDates;

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

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(float outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public float getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(float amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getReportDates() {
        return reportDates;
    }

    public void setReportDates(String reportDates) {
        this.reportDates = reportDates;
    }
}