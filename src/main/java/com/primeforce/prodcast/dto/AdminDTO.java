package com.primeforce.prodcast.dto;

/**
 * Created by sarathan732 on 5/16/2016.
 */
public class AdminDTO<T> extends ProdcastDTO {

    private T result;
    private String successMessage;

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public T getResult(){
        return result;
    }

    public void setResult(T result){
        this.result = result;
    }
}
