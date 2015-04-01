package com.fan2fan.form;

import org.hibernate.validator.constraints.Email;

public class InvitationForm {

    @Email
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
