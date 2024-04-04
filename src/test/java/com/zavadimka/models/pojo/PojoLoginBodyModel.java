package com.zavadimka.models.pojo;

public class PojoLoginBodyModel {

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    String email, password;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
