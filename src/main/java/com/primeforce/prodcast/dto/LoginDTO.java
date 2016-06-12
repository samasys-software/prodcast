package com.primeforce.prodcast.dto;

import com.primeforce.prodcast.businessobjects.Employee;

/**
 * Created by sarathan732 on 4/23/2016.
 */
public class LoginDTO extends ProdcastDTO{

    private boolean success;

    private Employee employee;

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
}
