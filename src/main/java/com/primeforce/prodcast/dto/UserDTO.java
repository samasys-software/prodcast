package com.primeforce.prodcast.dto;

/**
 * Created by sarathan732 on 4/26/2016.
 */
public class UserDTO {

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String userId;
    private String password;
}
