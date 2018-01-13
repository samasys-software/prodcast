package com.primeforce.prodcast.dto;

import com.primeforce.prodcast.businessobjects.Employee;

/**
 * Created by sarathan732 on 4/23/2016.
 */
public class LoginDTO extends ProdcastDTO{

    private boolean success;

    private Employee employee;

    private String timezone,authenticationKey;

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getAuthenticationKey() {
        return authenticationKey;
    }

    public void setAuthenticationKey(String authenticationKey) {
        this.authenticationKey = authenticationKey;
    }
}
