package com.primeforce.prodcast.businessobjects;

import java.util.List;

/**
 * Created by sarathan732 on 4/23/2016.
 */
public class Customer {

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    private String cellPhone;

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    private String unitNumber;

    public List<Bill> getOutstandingBill() {
        return outstandingBill;
    }

    public void setOutstandingBill(List<Bill> outstandingBill) {
        this.outstandingBill = outstandingBill;
    }

    private List<Bill> outstandingBill;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    private long id;
    private String customerName;
    private String billingAddress1;
    private String billingAddress2;

    public String getBillingAddress1() {
        return billingAddress1;
    }

    public void setBillingAddress1(String billingAddress1) {
        this.billingAddress1 = billingAddress1;
    }

    public String getBillingAddress2() {
        return billingAddress2;
    }

    public void setBillingAddress2(String billingAddress2) {
        this.billingAddress2 = billingAddress2;
    }

    public String getBillingAddress3() {
        return billingAddress3;
    }

    public void setBillingAddress3(String billingAddress3) {
        this.billingAddress3 = billingAddress3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    private String billingAddress3;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String customerType;
    private String area;
    private String firstname;
    private String customerId1;
    private String customerDesc1;
    private String customerId2;


    public String getCustomerDesc2() {
        return customerDesc2;
    }

    public void setCustomerDesc2(String customerDesc2) {
        this.customerDesc2 = customerDesc2;
    }

    public String getCustomerId2() {
        return customerId2;
    }

    public void setCustomerId2(String customerId2) {
        this.customerId2 = customerId2;
    }

    public String getCustomerDesc1() {
        return customerDesc1;
    }

    public void setCustomerDesc1(String customerDesc1) {
        this.customerDesc1 = customerDesc1;
    }

    public String getCustomerId1() {
        return customerId1;
    }

    public void setCustomerId1(String customerId1) {
        this.customerId1 = customerId1;
    }

    private String customerDesc2;
    private boolean smsAllowed;
    private boolean active;

    public boolean isSmsAllowed() {
        return smsAllowed;
    }

    public void setSmsAllowed(boolean smsAllowed) {
        this.smsAllowed = smsAllowed;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getFaxnumber() {
        return faxnumber;
    }

    public void setFaxnumber(String faxnumber) {
        this.faxnumber = faxnumber;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    private String lastname;
    private String title;
    private String phonenumber;
    private String faxnumber;
    private String emailaddress;
    private String notes;
    private String weekday;
    private long storeType;


    public long getStoreType() {
        return storeType;
    }

    public void setStoreType(long storeType)
    {
        this.storeType = storeType;
    }

}
