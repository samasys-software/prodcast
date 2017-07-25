package com.primeforce.prodcast.dto;

/**
 * Created by Hai on 11/10/2016.
 */
public class CustomerReportDTO  extends ProdcastDTO {



    private String header,attributes,reportName;
    private Object result;
    private float amount,outstandingBalance,amountPaid;
    private String reportDates;
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

    public String getReportDates() {
        return reportDates;
    }

    public void setReportDates(String reportDates) {
        this.reportDates = reportDates;
    }


    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
    public Object getTotalAmount()
    {
        return amount;
    }
    public Object setTotalAmount(float amount)
    {
        return  this.amount=amount;
    }

    public float getTotalOutstandingBalance()
    {
        return outstandingBalance;
    }
    public void setTotalOutstandingBalance(float outstandingBalance)
    {
        this.outstandingBalance=outstandingBalance;
    }
    public float  getTotalAmountPaid()
    {
        return amountPaid;
    }
    public void  setTotalAmountPaid(float amountPaid)
    {
        this.amountPaid=amountPaid;
    }


}