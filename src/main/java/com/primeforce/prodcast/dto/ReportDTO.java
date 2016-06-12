package com.primeforce.prodcast.dto;

import com.primeforce.prodcast.businessobjects.Collection;
import com.primeforce.prodcast.businessobjects.Order;

import java.util.List;
import java.util.Map;

/**
 * Created by sarathan732 on 5/1/2016.
 */
public class ReportDTO extends ProdcastDTO {

    private float totalSales;
    private float totalCollection;

    public String getReportDates() {
        return reportDates;
    }

    public void setReportDates(String reportDates) {
        this.reportDates = reportDates;
    }

    private String reportDates;

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    private float balance;

    public float getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(float totalSales) {
        this.totalSales = totalSales;
    }

    public float getTotalCollection() {
        return totalCollection;
    }

    public void setTotalCollection(float totalCollection) {
        this.totalCollection = totalCollection;
    }

    public Map<String,Float> getCollectionGroup() {
        return collectionGroup;
    }

    public void setCollectionGroup(Map<String, Float> collectionGroup) {
        this.collectionGroup = collectionGroup;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Collection> getCollections() {
        return collections;
    }

    public void setCollections(List<Collection> collections) {
        this.collections = collections;
    }

    private Map<String,Float> collectionGroup;
    private List<Order> orders;
    private List<Collection> collections;


}
