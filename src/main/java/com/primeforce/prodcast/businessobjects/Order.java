package com.primeforce.prodcast.businessobjects;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by sarathan732 on 4/26/2016.
 */
public class Order {

    private long orderId;
    private long billNumber;
    private long employeeId;
    private long customerId;
    private Timestamp createTime;
    private float totalAmount;
    private String customerName;
    private String employeeName;

    public List<Collection> getCollectionEntries() {
        return collectionEntries;
    }

    public void setCollectionEntries(List<Collection> collectionEntries) {
        this.collectionEntries = collectionEntries;
    }

    private List<Collection> collectionEntries;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public float getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(float outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    private float outstandingBalance;

    private Date billDate;
    private List<OrderEntry> orderEntries;

    public List<OrderEntry> getOrderEntries() {
        return orderEntries;
    }

    public void setOrderEntries(List<OrderEntry> orderEntries) {
        this.orderEntries = orderEntries;
    }

    public long getOrderId() {

        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(long billNumber) {
        this.billNumber = billNumber;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }


}
