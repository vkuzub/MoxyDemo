package com.moxydemo.data.network.model;

/**
 * Created by Vyacheslav on 31.05.2017.
 */

public class LoginResponse {

    private String status;
    private String message;
    private String token;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
