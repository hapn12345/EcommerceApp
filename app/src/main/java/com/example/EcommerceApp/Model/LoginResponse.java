package com.example.EcommerceApp.Model;


import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;
    @SerializedName("token")
    String token;
    String id;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LoginResponse (String token,String email, String password){
        this.email = email;
        this.password = password;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
