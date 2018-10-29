package com.amostra.net.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class UserResponse {

    private Boolean authenticated;
    private String message;
    private User user;
    private Token token;

    public UserResponse(){
        authenticated = false;
        message = "";
        user = new User();
        token = new Token();
    }

    public Boolean getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(Boolean authenticated) {
        this.authenticated = authenticated;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}