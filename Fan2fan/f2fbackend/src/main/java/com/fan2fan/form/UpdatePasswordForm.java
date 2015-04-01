package com.fan2fan.form;

import com.fan2fan.model.User;
import org.hibernate.validator.constraints.Length;

/**
 * Form used to update User Password
 * Created by huangsz on 2014/5/20.
 */
public class UpdatePasswordForm extends User {

    @Length(min = 6)
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
