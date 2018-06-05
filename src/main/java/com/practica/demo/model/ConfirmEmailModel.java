package com.practica.demo.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ConfirmEmailModel {

    @NotNull
    @Size(min = 4)
    private String email;
    @NotNull
    private String confirmationCode;

    public ConfirmEmailModel() {
    }

    public ConfirmEmailModel( String email, String confirmationCode) {
        this.email = email;
        this.confirmationCode = confirmationCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }
}
