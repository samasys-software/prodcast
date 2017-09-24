package com.primeforce.prodcast.dto;

import com.primeforce.prodcast.businessobjects.Distributor;
import com.primeforce.prodcast.businessobjects.Customer;
//import com.primeforce.prodcast.businessobjects.NewCustomerRegistrationDetails;

import java.util.List;

/**
 * Created by Hai on 11/2/2016.
 */
public class CustomerLoginDTO<T> extends ProdcastDTO {
    private boolean verified;
    private T result;

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    private boolean registered;
    private List<Distributor> distributors;

    private List<Customer> customers;

    private List<Distributor> DistributorsPublic;
    //private List<NewCustomerRegistrationDetails> newCustRegDetails;

    public T getResult(){
        return result;
    }

    public void setResult(T result){
        this.result = result;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public List<Distributor> getDistributors()
    {
        return distributors;
    }


    public void setDistributors(List<Distributor> distributors) {
        this.distributors = distributors;

    }
    public List<Distributor> getDistributorsPublic()
    {
        return DistributorsPublic;
    }

    public void setDistributorsPublic(List<Distributor> DistributorsPublic)
    {
        this.DistributorsPublic = DistributorsPublic;
    }

    public List<Customer> getAllCustomers()
    {
        return customers;
    }

    public void setAllCustomers(List<Customer> customers)
    {
        this.customers = customers;
    }





}