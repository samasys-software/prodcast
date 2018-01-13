package com.primeforce.prodcast.dto;

/**
 * Created by sarathan732 on 4/23/2016.
 */
public class ProdcastDTO {
    private boolean error,authenticationError,validationFailed;

    private String errorMessage,validationErrorMessage,authenticationErrorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public boolean isValidationFailed() {
        return validationFailed;
    }

    public void setValidationFailed(boolean validationFailed) {
        this.validationFailed = validationFailed;
    }

    public String getValidationErrorMessage() {
        return validationErrorMessage;
    }

    public void setValidationErrorMessage(String validationErrorMessage) {
        this.validationErrorMessage = validationErrorMessage;
    }

    public boolean isAuthenticationError() {
        return authenticationError;
    }

    public void setAuthenticationError(boolean authenticationError) {
        this.authenticationError = authenticationError;
    }

    public String getAuthenticationErrorMessage() {
        return authenticationErrorMessage;
    }

    public void setAuthenticationErrorMessage(String authenticationErrorMsg) {
        this.authenticationErrorMessage = authenticationErrorMsg;
    }
}
