package com.primeforce.prodcast.dto;

import com.primeforce.prodcast.businessobjects.Expense;

import java.util.List;

/**
 * Created by sarathan732 on 8/7/2016.
 */
public class ExpenseDTO extends ProdcastDTO {

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    private List<Expense> expenses;

}
